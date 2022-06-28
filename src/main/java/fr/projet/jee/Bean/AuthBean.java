package fr.projet.jee.Bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.*;
import javax.inject.Inject;

import fr.projet.jee.Dao.UserDao;
import fr.projet.jee.Objets.User;


import fr.projet.jee.Dao.TokenDao;
import fr.projet.jee.Objets.CustomPair;
import fr.projet.jee.Objets.Token;


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AuthBean {
    @Inject
    private UserDao _userDao;

    @Inject
    private TokenDao _tokenDao;

    public List<Token> getTokens(Long uid) {
        return _tokenDao.read(uid);
    }

    public User getUser(Long id) {
        return _userDao.getUserById(id);
    }
    public User getUser(String uname) {
        return _userDao.getUserByUName(uname);
    }
    
    public List<User> getUsers() {
        return _userDao.getAllUsers();
    }

    public List<Token> getTokens() {
        return _tokenDao.getAll();
    }
    
    public Token getToken(String val) {
        return _tokenDao.getTokenByValue(val);
    }
    public List<Token> getUserTokens(Long uid) {
        var res =_tokenDao.getAll().stream().filter(_token -> _token.getUser().getId().equals(uid)).collect(Collectors.toList());
        return res;
    }

    String HashPwd(String psswd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(psswd.getBytes());
        return new String(md.digest());
    }

    public CustomPair addUser(User _user) {
        try {
            var pwd = this.HashPwd(_user.getPassword());
            _user.setPassword(pwd);
            var isUserCreated = _userDao.create(_user);
            var isTokenAssigned = false;
            if(isUserCreated) {
                var dbo_user = _userDao.getUserByUName(_user.getUsername());
                if(dbo_user != null && dbo_user.getId() != null) {
                }
            }
            return new CustomPair(isUserCreated, isTokenAssigned);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new CustomPair(false, false);
        }
    }

    public String addToken(User u) {
        var _token = new Token();
        _token.setUser(u);
        if(_tokenDao.create(_token))
            return _token.getValue();
        else return "Echec";
    } 


    public String login(User user, String tokenVal) {
        var dbo_user = _userDao.getUserByUName(user.getUsername());
        var token = "";
        
        try {
            if(this.HashPwd(user.getPassword()).equals(dbo_user.getPassword()))
                token = this.addToken(dbo_user);
        } catch (Exception e) {
            token = "Echec";
            e.printStackTrace();
        }
        
        return token;
    }

    public boolean logout(String token_val) {
        var token = _tokenDao.getTokenByValue(token_val);
        if(token != null && token.getEndValidity() == null) {
            return _tokenDao.terminate(token);
        }
        return false;
    }

}