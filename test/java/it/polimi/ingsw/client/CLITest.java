package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.view.cli.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**JUnit Test methods for CLI class
 * @see it.polimi.ingsw.view.cli.CLI
 * @since 1.0
 * */

class CLITest{

    private static InputStream sysInBackup;
    private static CLI cli;



    @BeforeAll
    static void setStream(){
        cli=new CLI();
        cli.setActiveDebug(true);
    }

    @AfterAll
    static void resetStream(){

    }

    @Test
    void out(){

        try (BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/src/test/java/it/polimi/ingsw/resources/view.cli.CLI.chooseColumnTestInput.txt"))){

            for(int i=0; i<160; i++){
                cli.out(reader.readLine());
                cli.outln("");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void showBanner(){
        cli.showBanner();
    }

    @Test
    void inputInt(){

        try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/it/polimi/ingsw/resources/view.cli.CLI.chooseColumnTestInput.txt")){
            cli.setInputStream(inputStream);
            int input= cli.inputInt("input:\n");
            cli.outln("You typed "+input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void inputString(){
        try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/it/polimi/ingsw/resources/view.cli.CLI.chooseColumnTestInput.txt")){
            cli.setInputStream(inputStream);
            for(int i=0; i<160; i++){
                String input= cli.inputString("input:\n");
                System.out.println("You typed "+input);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void startInterface() {
        cli.startInterface();
    }

    @Test
    void infoMessage() {
        cli.infoMessage("INFO MESSAGE TEST");
    }

    @Test
    void errorMessage() {
        cli.errorMessage("ERROR MESSAGE TEST");
    }

    @Test
    void welcome() {

        try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/it/polimi/ingsw/resources/view.cli.CLI.welcomeTestInput.txt")){
            cli.setInputStream(inputStream);
            Object[] obj = cli.welcome();
            cli.outln(obj.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void showBoard(){
        Color[][] colorz={ { Color.Yellow, Color.LightBlue, Color.Pink , Color.White, Color.Blue,null,null,null,Color.Green },
                { Color.Yellow, Color.LightBlue, Color.Pink , Color.White, Color.Blue,null,null,null,Color.Green },
                { Color.Yellow, Color.LightBlue, Color.Pink , Color.White, Color.Blue,null,null,null,null },
                { Color.Green, Color.LightBlue, null , Color.White, null,Color.Yellow,Color.Yellow,Color.Yellow,null },
                { Color.Yellow, Color.LightBlue, Color.Pink , Color.White, Color.Blue,Color.Blue,Color.Blue,Color.Blue,Color.Blue },
                {Color.Yellow, Color.LightBlue, Color.Pink , Color.White, Color.Blue,null,null,null,null },
                { Color.Green, Color.LightBlue, null , Color.White, null, Color.White,Color.White,Color.White,Color.White },
                { Color.Green, Color.LightBlue, null , Color.White, null,Color.LightBlue,Color.LightBlue,Color.Pink,Color.Pink },
                { Color.Green, Color.LightBlue, null , Color.White, null,Color.Yellow,Color.LightBlue,Color.LightBlue,Color.LightBlue }};;
        Tile[][] matriz = new Tile[9][9];
        for(int i=0;i<9; i++){
            for(int j=0;j<9; j++){
                if(colorz[i][j]!= null){
                    matriz[i][j] = new Tile(colorz[i][j], Figure.Cats);
                }else{
                    matriz[i][j] = null;
                }
            }
        }

    }

    @Test
    void showBookshelf(){
        Color[][] colors={ { null, Color.LightBlue, null , Color.White, null },
                { Color.Yellow, Color.LightBlue, null , Color.White, null },
                { Color.Yellow, Color.LightBlue, Color.Pink , Color.White, null },
                { Color.Green, Color.LightBlue, Color.Yellow , Color.White, Color.Yellow },
                { Color.Yellow, Color.LightBlue, Color.Pink , Color.White, Color.Blue },
                {Color.Yellow, Color.LightBlue, Color.Pink , Color.White, Color.Blue }};;
        Tile[][] matrix = new Tile[6][5];
        for(int i=0;i<6; i++){
            for(int j=0;j<5; j++){
                if(colors[i][j]!= null){
                    matrix[i][j] = new Tile(colors[i][j],Figure.Cats);
                }else{
                    matrix[i][j] = null;
                }
            }
        }

    }

    @Test
    void interactiveMainMenu() {

        try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/it/polimi/ingsw/resources/view.cli.CLI.interactiveMainMenuTestInput.txt")){
            cli.setInputStream(inputStream);
            cli.interactiveMainMenu();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void pickTilesFromBoard() {

        try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/it/polimi/ingsw/resources/view.cli.CLI.pickTilesFromBoardTestInput.txt")){
            cli.setInputStream(inputStream);
            cli.pickTilesFromBoard();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void sortTilesToInsert() {

        List<Tile> coupleTiles = Arrays.asList(new Tile(Color.Pink, Figure.Cats),new Tile(Color.Pink, Figure.Cats));
        List<Tile> tripleTiles = Arrays.asList(new Tile(Color.Pink, Figure.Cats),new Tile(Color.Pink, Figure.Cats),new Tile(Color.Pink, Figure.Cats));

        try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/it/polimi/ingsw/resources/view.cli.CLI.sortTilesToInsertTestInput.txt")){
            cli.setInputStream(inputStream);
            cli.debugMessage("Couple of Tiles");
            cli.sortTilesToInsert(coupleTiles);
            cli.debugMessage("Triple of Tiles");
            cli.sortTilesToInsert(tripleTiles);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void chooseColumn() {
        try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/it/polimi/ingsw/resources/view.cli.CLI.chooseColumnTestInput.txt")){
            cli.setInputStream(inputStream);
            int col=cli.chooseColumn();
            cli.infoMessage("YOU CHOOSE COLUMN"+col);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void exitInterface() {
    }

    @Test
    void showPersonalGoalCard(){
        Color[][] colors={ { Color.Pink, null, Color.Blue , null, null },
                { null, null, null , null, Color.Green },
                { null, null, null , Color.White, null },
                { null, Color.Yellow, null , null, null },
                { null, null, null , null, null },
                {null, null, Color.LightBlue, null, null }};;
        Tile[][] matrix = new Tile[6][5];
        for(int i=0;i<6; i++){
            for(int j=0;j<5; j++){
                if(colors[i][j]!= null){
                    matrix[i][j] = new Tile(colors[i][j],Figure.Cats);
                }else{
                    matrix[i][j] = null;
                }
            }
        }
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(new Bookshelf(matrix),"ciao");

        

    }
}