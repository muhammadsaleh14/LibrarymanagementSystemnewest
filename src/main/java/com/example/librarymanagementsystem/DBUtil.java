
package com.example.librarymanagementsystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DBUtil {
    public static void closeConnection (PreparedStatement preparedStatement,ResultSet resultSet,Connection connection){
        if(preparedStatement != null){try{preparedStatement.close();} catch (SQLException e){e.printStackTrace();}}
        if(connection != null){try{connection.close();} catch (SQLException e){e.printStackTrace();}}
        if(resultSet != null){try{resultSet.close();} catch (SQLException e){e.printStackTrace();}}
    }
    public static void closeConnection (PreparedStatement preparedStatement,Connection connection){
        if(preparedStatement != null){try{preparedStatement.close();} catch (SQLException e){e.printStackTrace();}}
        if(connection != null){try{connection.close();} catch (SQLException e){e.printStackTrace();}}
    }

    //requesting permission from admin to create user
    public static Boolean requestUser(String username, String password_1, String contact) throws SQLException {
        Connection connection = null;PreparedStatement psInsert = null;PreparedStatement psCheckUserExists = null;ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","admin");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM request_user WHERE name = ?");
            psCheckUserExists.setString(1,username);
            resultSet = psCheckUserExists.executeQuery();
            if(resultSet.isBeforeFirst()){
                return false;
            }

            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
            psCheckUserExists.setString(1,username);
            resultSet = psCheckUserExists.executeQuery();
            if(resultSet.isBeforeFirst()){
                return false;
            }
            psInsert = connection.prepareStatement("INSERT INTO request_user (name,password,contact) VALUES (?,?,?)");
            psInsert.setString(1,username);
            psInsert.setString(2,password_1);
            psInsert.setString(3,contact);
            psInsert.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("ERRORRR");
            e.printStackTrace();}
        finally{
            if(resultSet != null){try{resultSet.close();} catch (SQLException e){e.printStackTrace();}}
            if(psInsert != null){try{psInsert.close();} catch (SQLException e){e.printStackTrace();}}
            if(psCheckUserExists != null){try{psCheckUserExists.close();} catch (SQLException e){e.printStackTrace();}}
            if(connection != null){try{connection.close();} catch (SQLException e){e.printStackTrace();}}
        }
        throw new SQLException();
    }

    public static void signUpUser(String username, String password_1, String contact) {

        Connection connection = null; PreparedStatement psInsert = null; PreparedStatement psCheckUserExists = null; ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","admin");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
            psCheckUserExists.setString(1,username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                return;
            }
            psInsert = connection.prepareStatement("INSERT INTO users (name,password,contact) VALUES (?,?,?)");
            psInsert.setString(1,username);
            psInsert.setString(2,password_1);
            psInsert.setString(3,contact);
            psInsert.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();}
        finally{
            if(resultSet != null){try{resultSet.close();} catch (SQLException e){e.printStackTrace();}}
            if(psInsert != null){try{psInsert.close();} catch (SQLException e){e.printStackTrace();}}
            if(psCheckUserExists != null){try{psCheckUserExists.close();} catch (SQLException e){e.printStackTrace();}}
            if(connection != null){try{connection.close();} catch (SQLException e){e.printStackTrace();}}
        }
    }

    public static boolean logInAdmin(String username, String password) throws Exception {
        Connection connection = null; PreparedStatement psCheckUserExists = null; ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","admin");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM admin WHERE name = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            if(resultSet.isBeforeFirst()){
                while(resultSet.next()){
                    String retrievedPassord = resultSet.getString("password");
                    if(retrievedPassord.equals(password)){
                        return true;
                    }
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception();
        }
        finally{
            closeConnection(psCheckUserExists,resultSet,connection);
        }
        return false;
    }

    public static boolean logInUser(String username, String password)  {
        Connection connection = null;PreparedStatement psCheckUserExists = null;ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            if(resultSet.isBeforeFirst()){
                while(resultSet.next()){
                    String retrievedPassord = resultSet.getString("password");
                    if(retrievedPassord.equals(password)){
                        return true;
                    }
                }
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            closeConnection(psCheckUserExists,resultSet,connection);
        }
        return false;
    }


    static void deleteRequest(String username) throws SQLException {
        Connection connection = null; PreparedStatement delete_requestUser = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","admin");
            delete_requestUser = connection.prepareStatement("DELETE FROM request_user WHERE name = ?;");
            delete_requestUser.setString(1,username);
            delete_requestUser.executeUpdate();
        }
        catch (SQLException e){

            e.printStackTrace();
            throw new SQLException();
        }
        finally{
           closeConnection(delete_requestUser,connection);
        }
    }


    static void acceptRequest(Users users) throws Exception {
        deleteRequest(users.getName());
        signUpUser(users.getName(), users.getPassword(), users.getContact());
    }

    static void addDate(String username, String date)  {

        Connection connection = null; PreparedStatement Date = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","admin");
            Date = connection.prepareStatement("UPDATE users " +
                                                    "SET date = ? " +
                                                    "WHERE name = ?;");
            Date.setString(1,date);
            Date.setString(2,username);
            Date.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("ERRORRR");
            e.printStackTrace();}
        finally{
            closeConnection(Date,connection);
        }
    }



    public static ObservableList<String> searchBooksList(String columnLabel, String textToSearch, String preparedStatement) throws SQLException {
        Connection connection = null;PreparedStatement search = null;ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","admin");
            search = connection.prepareStatement(preparedStatement);
            search.setString(1,textToSearch);
            resultSet = search.executeQuery();
            ObservableList<String> list = FXCollections.observableArrayList();
            while(resultSet.next()){
                list.add(resultSet.getString(columnLabel));
            }
            return list;
        }
        catch (SQLException e){
            System.out.println("ERRORRR");
            e.printStackTrace();}
        finally{
            closeConnection(search,resultSet,connection);
        }
        throw new SQLException();
    }
    public static boolean AddNewBook(String title, String author, String category, String language, String bookStatus, String arrivalDate,String imageURL) throws SQLException {
        Connection connection = null;PreparedStatement addBook = null;ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","admin");
            addBook = connection.prepareStatement("SELECT title FROM newbooks where title = ?");
            addBook.setString(1,title);
            resultSet = addBook.executeQuery();

            if(resultSet.isBeforeFirst()){
                return false;
            }
            addBook = connection.prepareStatement("INSERT INTO newbooks (title , author, category, languagee, bookstatus, arrivaldate,image)" +
                                                "VALUES (?, ?, ?, ?, ?, ?,?);");
            addBook.setString(1,title);
            addBook.setString(2,author);
            addBook.setString(3,category);
            addBook.setString(4,language);
            addBook.setString(5,bookStatus);
            addBook.setString(6,arrivalDate);
            addBook.setString(7,imageURL);
            addBook.executeUpdate();
            return true;
        }

        catch (SQLException e){
            System.out.println("ERRORRR");
            e.printStackTrace();
            throw new SQLException();
        }
        finally{
            closeConnection(addBook,resultSet,connection);
        }
    }
    public static void setAvailibilityForFreshArrival() throws SQLException {
        java.util.Date currentDate = new java.util.Date();
        java.util.Date arrivalDate;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Connection connection = null;PreparedStatement CheckAvailibility = null;ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","admin");
            CheckAvailibility = connection.prepareStatement("SELECT arrivaldate FROM newbooks");
            resultSet = CheckAvailibility.executeQuery();
            if(resultSet.isBeforeFirst()){
                while(resultSet.next()) {
                    String ArrivalDate = resultSet.getString("arrivaldate");
                    arrivalDate = sdf.parse(ArrivalDate);
                    long timePassed = currentDate.getTime() - arrivalDate.getTime();
                    if(((timePassed / (1000 * 60 * 60 * 24)) > 60)){
                        PreparedStatement setAvailibility;
                        setAvailibility = connection.prepareStatement("UPDATE newbooks SET bookstatus = 'Availible' Where arrivaldate = ?");
                        setAvailibility.setString(1,ArrivalDate);
                        setAvailibility.executeUpdate();
                    }
                    if(((timePassed / (1000 * 60 * 60 * 24)) < 60)){
                        PreparedStatement setAvailibility;
                        setAvailibility = connection.prepareStatement("UPDATE newbooks SET bookstatus = 'Fresh Arrival' Where arrivaldate = ?");
                        setAvailibility.setString(1,ArrivalDate);
                        setAvailibility.executeUpdate();
                    }
                }
            }
        }

        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally{
            closeConnection(CheckAvailibility,resultSet,connection);
        }
    }
    public static void addExistingbook(String title, boolean add) throws SQLException {
        Connection connection = null;PreparedStatement addBook = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","admin");
            if(add){
                addBook = connection.prepareStatement("UPDATE newbooks SET copiesOwned = copiesOwned + 1 " +
                        "WHERE title = ?;");
            }
            else {
                addBook = connection.prepareStatement("UPDATE newbooks SET copiesOwned = copiesOwned - 1 " +
                        "WHERE title = ?;");
            }
            addBook.setString(1,title);
            addBook.executeUpdate();
            if(!add){
                addBook = connection.prepareStatement("DELETE FROM newbooks WHERE title = ? AND copiesOwned = 0;");
                addBook.setString(1,title);
                addBook.executeUpdate();
            }

        }
        catch (SQLException e){
            System.out.println("ERRORRR");
            e.printStackTrace();
            throw new SQLException();
        }finally{
            closeConnection(addBook,connection);
        }
    }
    public static void searchBooksTable(String textToSearch, TableView<Books> table) throws SQLException {
        Connection connection = null;
        PreparedStatement search = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            search = connection.prepareStatement(" SELECT * FROM newbooks WHERE title LIKE ? " +
                 "OR author LIKE ? OR category like ? Or Languagee like ? or bookstatus like ?");
            search.setString(1,textToSearch);
            search.setString(2,textToSearch);
            search.setObject(3,textToSearch);
            search.setObject(4,textToSearch);
            search.setString(5,textToSearch);
            setBooksInTable(search,table);

        }
        finally{
            closeConnection(search,connection);
        }
    }
    public static void reloadTableView(TableView<Books> table) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
        PreparedStatement insert = connection.prepareStatement("SELECT * FROM newbooks");
        setBooksInTable(insert,table);
    }
    public static void setBooksInTable(PreparedStatement insert, TableView<Books> table) throws SQLException {
        ObservableList<Books> list = FXCollections.observableArrayList();
        ResultSet resultSet = insert.executeQuery();
        while (resultSet.next()) {
            Books a = new Books(resultSet.getString("title"), resultSet.getString("author")
                    , resultSet.getString("category"), resultSet.getString("languagee"),
                    resultSet.getString("bookstatus"), resultSet.getString("arrivaldate")
                    , resultSet.getString("copiesOwned"));
            String b;
            if ( (b= resultSet.getString("image")) != null && !(Objects.equals(resultSet.getString("image"), "")) ) {
                try {
                    ImageView image = new ImageView(new Image(resultSet.getString("image")));
                    image.setFitHeight(70);
                    image.setFitWidth(50);
                    image.setPreserveRatio(false);
                    a.setImageView(image);
                }catch (IllegalArgumentException e){
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
                    PreparedStatement deleteLostImageURL = connection.prepareStatement("UPDATE newbooks " +
                            "SET image = null " +
                            "WHERE image = ?");
                    deleteLostImageURL.setString(1,resultSet.getString("image"));
                    deleteLostImageURL.executeUpdate();
                    throw new IllegalArgumentException();
                }
            }
            list.add(a);

        }
        table.setItems(list);
    }
    public static boolean updateExistingBook(String newTitle,String oldTitle, String author, String category, String language,String imageURL) throws SQLException {
        Connection connection = null;PreparedStatement addBook = null;ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","admin");
            addBook = connection.prepareStatement("SELECT title FROM newbooks where title = ? and title != ?");
            addBook.setString(1,newTitle);
            addBook.setString(2,oldTitle);
            resultSet = addBook.executeQuery();
           if(resultSet.isBeforeFirst()){
               return false;
           }
            addBook = connection.prepareStatement("UPDATE newbooks SET title = ? , author = ?, category = ?, languagee = ?," +
                                                        " image = ? where title = ?");
            addBook.setString(1,newTitle);
            addBook.setString(2,author);
            addBook.setString(3,category);
            addBook.setString(4,language);
            addBook.setString(5,imageURL);
            addBook.setString(6,oldTitle);
            addBook.executeUpdate();
            return true;
        }

        catch (SQLException e){
            System.out.println("ERRORRR");
            e.printStackTrace();
            throw new SQLException();
        }
        finally{
            closeConnection(addBook,resultSet,connection);
        }
    }
    public static ObservableList<Users> tableAllUsers(String search) throws SQLException {
        Connection connection = null;PreparedStatement getAllUsers = null;ResultSet resultSet = null;
        try {
            search = search.replaceAll("","%");
            search = search.replaceAll("\\s","%");
            ObservableList<Users> obList = FXCollections.observableArrayList();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            getAllUsers = connection.prepareStatement("SELECT * FROM users where name LIKE ? OR " +
                                                                                     " contact LIKE ? OR" +
                                                                                     " date LIKE ?");
            getAllUsers.setString(1,search);
            getAllUsers.setString(2,search);
            getAllUsers.setString(3,search);
            resultSet = getAllUsers.executeQuery();
            while(resultSet.next()) {
                Users a = Users.UsersBuilder.anUsers().buildName(resultSet.getString("name")).
                                             buildContact(resultSet.getString("contact")).
                                             buildDate(resultSet.getString("date")).
                                             build();
                obList.add(a);
            }
                return obList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
        finally{
            closeConnection(getAllUsers,resultSet,connection);
        }
    }
//    public static void searchTable(String textToSearch,TableView<Books> table) throws SQLException {
//        Connection connection = null;
//        PreparedStatement search = null;
//        ResultSet resultSet = null;
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
//            search = connection.prepareStatement(" SELECT * FROM newbooks WHERE title LIKE ? " +
//                    "OR author LIKE ? OR category like ? Or Languagee like ? or bookstatus like ?".replace('\'', ' '));
//            search.setString(1,textToSearch);
//            search.setString(2,textToSearch);
//            search.setObject(3,textToSearch);
//            search.setObject(4,textToSearch);
//            search.setString(5,textToSearch);
//            System.out.println(search);
//            setBooksInTable(search,table);
//
//        }
//        finally{
//            if(search != null){try{search.close();} catch (SQLException e){e.printStackTrace();};}
//            if(connection != null){try{connection.close();} catch (SQLException e){e.printStackTrace();};}
//        }
//    }
    public static void searchUsers(String textToSearch,TableView<Users> table) throws SQLException {
        Connection connection = null;
        PreparedStatement search = null;
        ResultSet resultSet;
        try {
            textToSearch = textToSearch.replaceAll("","%");
            textToSearch = textToSearch.replaceAll("\\s","");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            search = connection.prepareStatement("SELECT * FROM users WHERE name LIKE ?");
            search.setString(1,textToSearch);
            resultSet = search.executeQuery();
            ObservableList<Users> oList = FXCollections.observableArrayList();
            Users a;
            while(resultSet.next()){
                a = Users.UsersBuilder.anUsers().buildName(resultSet.getString("name")).
                                                 buildContact(resultSet.getString("contact")).
                                                 buildDate(resultSet.getString("date")).build();
                oList.add(a);
            }
            table.setItems(oList);
        }finally {
            closeConnection(search,connection);
        }
    }
    static boolean deleteUser(String name) {
        ResultSet resultSet;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                preparedStatement = connection.prepareStatement("DELETE FROM users WHERE name = ?;");
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        finally{
            closeConnection(preparedStatement,connection);
        }

    }
    public static void issueRequest(String title) throws SQLException {
        ResultSet resultSet = null; Connection connection = null; PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
//            SELECT column_names
//FROM table_name
//WHERE column_name IS NULL;
            preparedStatement = connection.prepareStatement("SELECT name FROM users " +
                                                            "WHERE bookIssued IS NULL AND name = ?");
            preparedStatement.setString(1,LoginController.issueCredentials.getName());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                preparedStatement = connection.prepareStatement("UPDATE users SET " +
                        "issueRequest = ? WHERE name = ? AND (bookIssued IS null OR bookIssued = '')");
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, LoginController.issueCredentials.getName());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        finally{
            closeConnection(preparedStatement,resultSet,connection);
        }
    }
//    public static void issueBook(String title) throws SQLException {
//        ResultSet resultSet = null;Connection connection = null; PreparedStatement preparedStatement = null;
//        try {
//            java.util.Date dt = new java.util.Date();
//            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//            String currentTime = sdf.format(dt);
//
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
//
//            preparedStatement = connection.prepareStatement("UPDATE users SET " +
//                                        "issueRequest = ?, dateOfIssue = ?  WHERE name = ?");
//            preparedStatement.setString(1, title);
//            preparedStatement.setString(2,currentTime);
//            preparedStatement.setString(3, LoginController.issueCredentials.getName());
//            preparedStatement.executeUpdate();
//
//            preparedStatement = connection.prepareStatement("SELECT COUNT(bookIssued) FROM users" +
//                                                                 " where bookIssued = ?");
//            preparedStatement.setString(1,title);
//            resultSet = preparedStatement.executeQuery();
//            int numberOfBooksIssued = 0;
//            while(resultSet.next()){
//                numberOfBooksIssued = resultSet.getInt(0);
//            }
//
//            preparedStatement = connection.prepareStatement("UPDATE newbooks " +
//                                                "SET copiesBorrowed = ?" +
//                                                "WHERE title = ?");
//            preparedStatement.setInt(1,numberOfBooksIssued);
//            preparedStatement.setString(2,title);
//            preparedStatement.executeUpdate();
//            //setting status for books
//            preparedStatement = connection.prepareStatement("UPDATE newbooks" +
//                    "SET bookstatus = Not Availible" +
//                    "WHERE copiesBorrowed >= copiesOwned;");
//            preparedStatement.executeUpdate();
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
    public static String issuedBook(String name) throws SQLException {
        ResultSet resultSet = null; Connection connection = null; PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("SELECT bookIssued FROM users WHERE name = ?");
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("bookIssued");


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
        finally{
            closeConnection(preparedStatement,resultSet,connection);
        }
    }
    public static boolean issueBook(String userName,String title) throws SQLException {
        Connection connection = null;PreparedStatement preparedStatement = null;ResultSet resultSet;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("SELECT copiesOwned,copiesBorrowed FROM newbooks WHERE title = ?");
            preparedStatement.setString(1,title);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
             int copiesOwned =  resultSet.getInt("copiesOwned");
             int copiesBorrowed = resultSet.getInt("copiesBorrowed");
             if(copiesBorrowed >= copiesOwned){
                 return false;
             }
            }
            preparedStatement = connection.prepareStatement("UPDATE users SET bookIssued = issueRequest WHERE name = ?");
            preparedStatement.setString(1,userName);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("UPDATE users SET issueRequest = null WHERE name = ?");
            preparedStatement.setString(1,userName);
            preparedStatement.executeUpdate();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            java.util.Date currentDate = new Date();
            String currentDateString = sdf.format(currentDate);
            preparedStatement = connection.prepareStatement("UPDATE users SET dateOfIssue = ? WHERE name = ?");
            preparedStatement.setString(1,currentDateString);
            preparedStatement.setString(2,userName);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("UPDATE newbooks SET copiesBorrowed = copiesBorrowed+1 WHERE title = ?");
            preparedStatement.setString(1,title);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("UPDATE newbooks SET bookStatus = 'Not Availible' WHERE copiesBorrowed >= " +
                                                                "copiesOwned");
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
        finally{
            closeConnection(preparedStatement,connection);
        }
        return true;
    }


    public static void denyIssue(String userName) {
        Connection connection = null;PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("UPDATE users SET issueRequest = null WHERE name = ?;");
            preparedStatement.setString(1,userName);
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        finally{
            closeConnection(preparedStatement,connection);
        }
    }

    public static ObservableList<Users> ibTable(String name) throws SQLException, ParseException {
        Connection connection = null;PreparedStatement getAllUsers = null;ResultSet resultSet = null;
        try {
            name = name.replaceAll("","%");
            name = name.replaceAll("\\s","%");
            ObservableList<Users> obList = FXCollections.observableArrayList();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            getAllUsers = connection.prepareStatement("SELECT * FROM library.users WHERE bookIssued IS NOT NULL AND bookIssued != ''" +
                                                        " AND name LIKE ? ");
            getAllUsers.setString(1,name);
            resultSet = getAllUsers.executeQuery();
            while(resultSet.next()) {

                Users a = Users.UsersBuilder.anUsers().buildName(resultSet.getString("name")).
                        buildBookIssued(resultSet.getString("bookIssued")).
                        buildDateOfIssue(resultSet.getString("dateOfIssue")).
                        buildDaysToReturn(Methods.bookIssueReturnTime(resultSet.getString("dateOfIssue"))).build();
                        obList.add(a);
            }
            return obList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        finally{
            closeConnection(getAllUsers,resultSet,connection);
        }

    }
    public static void returnBook(String userName,String title) throws SQLException, ParseException {
        Connection connection = null;PreparedStatement preparedStatement = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("UPDATE users SET bookIssued = NULL WHERE name = ?");
            preparedStatement.setString(1,userName);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("UPDATE users SET dateOfIssue = NULL WHERE name = ?");
            preparedStatement.setString(1,userName);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("UPDATE newbooks SET copiesBorrowed = copiesBorrowed-1 WHERE title = ?");
            preparedStatement.setString(1,title);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("UPDATE newbooks SET bookStatus = 'Availible' WHERE copiesOwned >= " +
                    "copiesBorrowed AND bookStatus = 'Not Availible'");
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        finally{
            closeConnection(preparedStatement,connection);
        }
    }
    public static void searchListViewUsers(String userName, ListView<String> listView) throws SQLException, ParseException {
        Connection connection = null;PreparedStatement preparedStatement = null;ResultSet resultSet = null;
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            userName = userName.replaceAll("","%");
            userName = userName.replaceAll(" ","%");
            preparedStatement = connection.prepareStatement("Select name from users where name LIKE ?");
            preparedStatement.setString(1,userName);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String a = resultSet.getString("name");
                observableList.add(a);

            }
            listView.setItems(observableList);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        finally{
            closeConnection(preparedStatement,resultSet,connection);
        }
    }

    public static Boolean checkUserExists(String text) {
        Connection connection = null;PreparedStatement preparedStatement = null;ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("Select name FROM users WHERE name = ?");
            preparedStatement.setString(1,text);
            resultSet = preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        finally{
            closeConnection(preparedStatement,resultSet,connection);
        }

    }

    public static void assignFine(String userName,String fineReason, int fineAmount) throws SQLException {
        Connection connection = null;PreparedStatement preparedStatement = null;ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("UPDATE users " +
                                                            "SET fineReason = ?,fineAmount = ? WHERE name = ?;");
            preparedStatement.setString(1,fineReason);
            preparedStatement.setInt(2,fineAmount);
            preparedStatement.setString(3,userName);
//            UPDATE table_name
//SET column1 = value1, column2 = value2, ...
//WHERE condition;
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
        finally{
            closeConnection(preparedStatement,resultSet,connection);
        }
    }

    public static ObservableList<Users> fineTableView(String search) throws SQLException {
        Connection connection = null;PreparedStatement preparedStatement = null;ResultSet resultSet = null;
        try {
            search = search.replaceAll("","%");
            search = search.replaceAll("\\s","%");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("Select name,fineAmount,fineReason from users WHERE fineAmount != 0" +
                    " And fineAmount IS NOT NULL AND name LIKE ?");
            preparedStatement.setString(1,search);
            resultSet = preparedStatement.executeQuery();
            ObservableList<Users> observableList = FXCollections.observableArrayList();
            while(resultSet.next()){
                Users a = Users.UsersBuilder.anUsers().buildName(resultSet.getString("name"))
                        .buildFineAmount(resultSet.getInt("fineAmount")).
                        buildFineReason(resultSet.getString("fineReason")).build();
                observableList.add(a);
            }
            return observableList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
        finally{
            closeConnection(preparedStatement,resultSet,connection);
        }
    }

    public static int checkFine(String name) throws SQLException {
        Connection connection = null;PreparedStatement preparedStatement = null;ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("SELECT fineAmount FROM users where name = ?");
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("fineAmount");
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
        finally{
            closeConnection(preparedStatement,resultSet,connection);
        }
    }

    public static ObservableList<Users> issuedRequestsTable() throws SQLException {
        Connection connection = null;PreparedStatement preparedStatement = null;ResultSet resultSet = null;
        ResultSet resultSet2;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("SELECT name, issueRequest FROM users where issueRequest IS NOT NULL" +
                                                                " AND issueRequest != ''");
            resultSet = preparedStatement.executeQuery();
            ObservableList<Users> observableList = FXCollections.observableArrayList();
            while(resultSet.next()){
                preparedStatement = connection.prepareStatement("SELECT copiesOwned - copiesBorrowed AS copiesAvailible " +
                                                                    " FROM newbooks WHERE title = ?");
                preparedStatement.setString(1,resultSet.getString("issueRequest"));
                resultSet2 = preparedStatement.executeQuery();
                resultSet2.next();
                Users a = Users.UsersBuilder.anUsers().buildName(resultSet.getString("name")).
                                                        buildIssueRequest(resultSet.getString("issueRequest"))
                                                        .buildCopiesAvailible(resultSet2.getInt("copiesAvailible")).build();

                observableList.add(a);
            }
            return observableList;
        }

        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
        finally{
            closeConnection(preparedStatement,resultSet,connection);
        }
    }

    public static boolean removeFine(String name) throws SQLException {
        Connection connection = null;PreparedStatement preparedStatement = null;ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            preparedStatement = connection.prepareStatement("SELECT name FROM users WHERE name = ?");
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                preparedStatement = connection.prepareStatement("UPDATE users SET fineAmount = 0 WHERE name = ?");
                preparedStatement.setString(1,name);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement("UPDATE users SET fineReason = NULL WHERE name = ?");
                preparedStatement.setString(1,name);
                preparedStatement.executeUpdate();
                return true;

            }
            else{
                return false;
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
        finally{
            closeConnection(preparedStatement,resultSet,connection);
        }
    }
}





