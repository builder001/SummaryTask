package ua.nure.tereshchenko.SummaryTask4.web.command;

import org.apache.log4j.Logger;

import ua.nure.tereshchenko.SummaryTask4.Path;
import ua.nure.tereshchenko.SummaryTask4.db.DBManager;
import ua.nure.tereshchenko.SummaryTask4.db.Role;
import ua.nure.tereshchenko.SummaryTask4.db.UserLock;
import ua.nure.tereshchenko.SummaryTask4.db.entity.User;
import ua.nure.tereshchenko.SummaryTask4.exception.AppException;
import ua.nure.tereshchenko.SummaryTask4.password.Password;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Login command.
 *
 * @author A.Tereshchenko
 */
public class LoginCommand extends Command {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("Command 'loginCommand' starts");

        HttpSession httpSession = request.getSession();

        // obtain login and password from a request
        DBManager manager = DBManager.getInstance();
        String login = request.getParameter("login");
        LOG.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new AppException("Login/password cannot be empty");
        }

        User user = manager.findUserByLogin(login);
        LOG.trace("Found in DB: user --> " + user);

        String local = null;
        if (httpSession.getAttribute("local") != null) {
            local = httpSession.getAttribute("local").toString();
        }

        String hashPassword = null;
        LOG.trace("Start hashing of password");
		try {
			hashPassword = Password.hash(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		LOG.trace("Password hashed");

        if (hashPassword != null && (user == null || !hashPassword.equals(user.getPassword()))) {
            if ((local != null) && (local.equals("en"))) {
                throw new AppException("Cannot find user with such login/password");
            } else if ((local == null) || (local.equals("ru"))) {
                throw new AppException("С таким логином/паролем пользователя не существует");
            }
        }

        UserLock userLock = null;
        if (user != null) {
            userLock = UserLock.getUserLock(user);
        }
        LOG.trace("userLock --> " + userLock);
        if (userLock == UserLock.LOCK) {
            if ((local != null) && (local.equals("en"))) {
                throw new AppException("User locked");
            } else if ((local == null) || (local.equals("ru"))) {
                throw new AppException("Пользователь заблокирован");
            }
        }

        Role userRole = null;
        if (user != null) {
            userRole = Role.getRole(user);
        }
        LOG.trace("userRole --> " + userRole);

        String forward = Path.PAGE_ERROR_PAGE;

        if (userRole == Role.ADMIN) {
            forward = Path.COMMAND_ADMIN;
        }

        if (userRole == Role.READER) {
            forward = Path.COMMAND_USER;
        }

        httpSession.setAttribute("user", user);
        LOG.trace("Set the session attribute: user --> " + user);

        httpSession.setAttribute("userRole", userRole);
        LOG.trace("Set the session attribute: userRole --> " + userRole);

        if (userRole != null) {
            LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());
        }

        LOG.debug("forward --> " + forward);
        LOG.debug("Command 'loginCommand' finished");

        return forward;
    }
}
