import java.util.*;

public class Mines {
    static char mine[][]=new char[8][8];
    static char bombs[][] = new char[8][8];
    static int numbers[][] = new int[8][8];
    static char numberToShow[][] = new char[8][8];
    static char mineSymbol ='*';
    static char safeSymbol ='-';
    static int hideCount = 54;
    static boolean canPlay = false;
    static boolean gameWon = false;
    static boolean gameLost =false;
    static int col=-1;
    static int row=-1;
    static int bombCount = 10;
    static Scanner sc=new Scanner(System.in);
    static Random rand = new Random();
    public static void main(String[] args){
        initialSetup();
        while(canPlay){

            printMine();
            getPosition();
            check();
        }
        if(gameWon){
            System.out.println("Hurrah!! You Won");
        }
        if(gameLost) {
            System.out.println("You Lost");
            printBombs();
        }



    }
    static boolean initialSetup(){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                mine[i][j]=mineSymbol;
            }
        }

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                bombs[i][j]=safeSymbol;
            }
        }

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                numberToShow[i][j]='F';
            }
        }
        printMine();
        getPosition();
        setBombs();
        setNumbers();
        mine[row][col]=safeSymbol;
        hideCount--;
        check();
        canPlay=true;
        return true;
    }
    static void getPosition(){
        do {
            System.out.println("enter the row and column:");
            row = sc.nextInt();
            col = sc.nextInt();
        }while(mine[row][col]!=mineSymbol);
    }
    static void printMine(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){

                if(row != -1 && col != -1){

                    if(canPrintNumbers(i,j)){
                        System.out.print(numbers[i][j]+" ");
                    }
                    else{
                        System.out.print(mine[i][j]+" ");
                    }
                }else{
                     System.out.print(mine[i][j]+" ");
                }


            }
            System.out.println();
        }
    }

    static void printBombs(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                System.out.print(bombs[i][j]+" ");
            }
            System.out.println();
        }
    }
    static boolean canPrintNumbers(int numRow, int numCol){

           if(numbers[numRow][numCol]!=0 && numberToShow[numRow][numCol]=='T'){
               return true;
           }

            return false;
    }

    static void setBombs(){
        int firstRow = row;
        int firstCol= col;
        for(int i=0; i<bombCount ;i++){
            int bombRow;
            int bombCol;
            do{
                bombRow = rand.nextInt(8);
                bombCol = rand.nextInt(8);
            }while((firstRow==bombRow && firstCol==bombCol) || (firstRow-1 == bombRow && firstCol-1 == bombCol) || (firstRow-1 == bombRow && firstCol == bombCol) ||
            (firstRow-1== bombRow && firstCol+1 ==bombCol )|| (firstRow+1== bombRow && firstCol+1 ==bombCol )||(firstRow == bombRow && firstCol-1 ==bombCol) || (firstRow==bombRow && firstCol+1==bombCol) ||
                    (firstRow+1==bombRow && firstCol-1==bombCol) || (firstRow+1 == bombRow && firstCol==bombCol) ||(firstRow+1==bombRow && firstCol==bombCol) ||bombs[bombRow][bombCol]=='B');
            bombs[bombRow][bombCol] = 'B';
        }
    }

    static void setNumbers(){
        for(int i=0;i<8; i++){
            for(int j=0; j<8 ; j++){
                int bombCount =0;
                int left = j-1;
                int right = j+1;
                int top = i-1;
                int bottom = i+1;
                if(bombs[i][j]!='B'){
                    //top left
                    if((left >=0 && top >=0) && bombs[top][left] == 'B'){
                        bombCount++;
                    }

                    //top
                    if((top >= 0) && bombs[top][j] == 'B'){
                        bombCount++;
                    }

                    //top right
                    if((top >= 0 && right < 8) && bombs[top][right] == 'B'){
                        bombCount++;
                    }

                    //left
                    if((left >=0) && bombs[i][left] == 'B'){
                        bombCount++;
                    }

                    // right
                    if((right < 8) && bombs[i][right] == 'B'){
                        bombCount++;
                    }

                    //bottom left
                    if((left >= 0 && bottom < 8) && bombs[bottom][left] == 'B'){
                        bombCount++;
                    }

                    //bottom
                    if((bottom < 8) && bombs[bottom][j] == 'B'){
                        bombCount++;
                    }

                    //bottom right
                    if((bottom < 8 && right < 8) && bombs[bottom][right] == 'B' ){
                        bombCount++;
                    }

                }
                numbers[i][j] = bombCount;

            }
        }
    }
    static void check(){
        if(bombs[row][col]=='B'){
            canPlay = false;
            gameLost=true;
        }
        else if(numbers[row][col]>0){
            numberToShow[row][col]='T';
            hideCount--;
        }
        else {
            mine[row][col] = safeSymbol;

                int left, right, top, bottom;
                //top left
                left = col - 1;
                top = row - 1;
                while (top >= 0 && left >= 0) {
                    if (numbers[top][left] != 0) {
                        numberToShow[top][left] = 'T';
                        hideCount--;
                        break;
                    } else if (bombs[top][left] != 'B') {
                        mine[top][left] = safeSymbol;
                        hideCount--;
                    }
                    top--;
                    left--;

                }


                //top right
                top = row - 1;
                right = col + 1;
                while (top >= 0 && right < 8) {
                    if (numbers[top][right] != 0) {
                        numberToShow[top][right] = 'T';
                        hideCount--;
                        break;
                    } else if (bombs[top][right] != 'B') {
                        mine[top][right] = safeSymbol;
                        hideCount--;
                    }
                    top--;
                    right++;

                }


                //bottom left
                left = col - 1;
                bottom = row + 1;
                while (bottom < 8 && left >= 0) {
                    if (numbers[bottom][left] != 0) {
                        numberToShow[bottom][left] = 'T';
                        hideCount--;
                        break;
                    } else if (bombs[bottom][left] != 'B') {
                        mine[bottom][left] = safeSymbol;
                        hideCount--;
                    }
                    bottom++;
                    left--;

                }

                //bottom right
                bottom = row + 1;
                right = col + 1;
                while (bottom < 8 && right < 8) {
                    if (numbers[bottom][right] != 0) {
                        numberToShow[bottom][right] = 'T';
                        hideCount--;
                        break;
                    } else if (bombs[bottom][right] != 'B') {
                        mine[bottom][right] = safeSymbol;
                        hideCount--;
                    }
                    bottom++;
                    right++;

                }

                //left
                left = col - 1;

                while (left >= 0) {
                    if (numbers[row][left] != 0) {
                        numberToShow[row][left] = 'T';
                        hideCount--;
                        break;
                    } else if (bombs[row][left] != 'B') {
                        mine[row][left] = safeSymbol;
                        hideCount--;
                    }

                    left--;

                }

                //right
                right = col + 1;
                while (right < 8) {
                    if (numbers[row][right] != 0) {
                        numberToShow[row][right] = 'T';
                        hideCount--;
                        break;
                    } else if (bombs[row][right] != 'B') {
                        mine[row][right] = safeSymbol;
                        hideCount--;
                    }

                    right++;

                }

                //top
                top = row - 1;
                while (top >= 0) {
                    if (numbers[top][col] != 0) {
                        numberToShow[top][col] = 'T';
                        hideCount--;
                        break;
                    } else if (bombs[top][col] != 'B') {
                        mine[top][col] = safeSymbol;
                        hideCount--;
                    }

                    top--;

                }

                //bottom
                bottom = row + 1;
                while (bottom < 8) {
                    if (numbers[bottom][col] != 0) {
                        numberToShow[bottom][col] = 'T';
                        hideCount--;
                        break;
                    } else if (bombs[bottom][col] != 'B') {
                        mine[bottom][col] = safeSymbol;
                        hideCount--;
                    }

                    bottom++;

                }



        }
        if(hideCount<=0){
            canPlay=false;
            gameWon=true;
        }
    }

}
