package beans;

import enums.Role;

import java.util.HashMap;

public class User {

    private String username;
    private String password;
    private Role role;


    public User(String login, String password){
        this.username = login;
        this.password = password;
        this.role = Role.USER;
    }

    public User(String login, String password, Role role){
        this.username = login;
        this.password = password;
        this.role = role;
    }

    public static HashMap<String,String> init(){
        HashMap<String,String> users = new HashMap<String,String>();
        users.put("user","user");
        users.put("user1","user2");
        users.put("user1","user2");
        return users;
    }

    public static boolean checkUser(HashMap<String,String> users,String login, String password){
        if(users.containsKey(login)){
            String pass = users.get(login);
            if(password.equals(pass))
                return true;

            return false;
        }else{
            return false;
        }
    }

    public String getLogin() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Role getRole() {
        return this.role;
    }
}
