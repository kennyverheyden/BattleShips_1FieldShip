package battleships;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BattleShips {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		welcome();
		int maxShipsPlayer=3; 			//How many ships for each player
		int boardSize=boardSize(); 		//Dynamic board size from user input
		Scanner input = new Scanner(System.in);

		String[] letterCoordinates=createLetterCoordinatesArr();		//Create alphabet list for coordinates
		String[][] field=createFieldArr(boardSize,letterCoordinates);	//Create board
		printFieldArr(field); 											//Print board
		String[]playerShipsCoordinates= playerShips(letterCoordinates, boardSize, maxShipsPlayer);
		String[]computerShipsCoordinates= computerShipsCoordinates(letterCoordinates, boardSize, maxShipsPlayer);
		System.out.println("My field: ");
		printFieldPlay(field, playerShipsCoordinates);
		System.out.println("\nPress enter to battle");
		input.nextLine();
		boolean win=battleShips(playerShipsCoordinates, computerShipsCoordinates, maxShipsPlayer, field, letterCoordinates, boardSize);
		if(win)
		{
			winMsg();
		}
	}

	private static void winMsg() {
		System.out.println("  *************************************************************************");
		System.out.println("  *****                                                               *****");
		System.out.println("  *****                      YOU WON THE GAME                         *****");
		System.out.println("  *****                                                               *****");
		System.out.println("  *************************************************************************\n");
	}

	private static void welcome() {
		System.out.println("  *************************************************************************");
		System.out.println("  *****                         Battle Ships                          *****");
		System.out.println("  *****           Defeat the computer ships in the ocean             *****");
		System.out.println("  *************************************************************************\n");
	}

	private static void printFieldPlay(String[][]field, String[]coordinatesArr) {
		for (int i = 0; i < field.length; i++)
		{
			printFieldLineDivider(field.length);
			System.out.println("");
			for (int j = 0; j < field.length; j++) {
				if(j==0)				{
					System.out.print(" | ");
				}
				int x=0;
				boolean set = false;
				while(x<coordinatesArr.length)
				{
					if(field[i][j].equals(coordinatesArr[x]))
					{
						System.out.print("** | ");
						set = true;
						break;
					}
					x++;
				}
				if(!set)
				{
					System.out.print(field[i][j]+" | ");
				}
			}
			System.out.println("");
		}
		printFieldLineDivider(field.length);
	}

	private static boolean battleShips(String[] playerShipsCoordinates, String[] computerShipsCoordinates, int maxShipsPlayer, String[][] fields, String[] letterCoordinates, int boardSize) {

		int playerShips=maxShipsPlayer;
		int computerShips=maxShipsPlayer;
		int checkNumberExist;
		String computerChoice;
		String inputCoordinate;
		Scanner input = new Scanner(System.in);
		Random rn = new Random();
		StringBuilder sb = new StringBuilder();
		char checkLetterExist;
		boolean valid; // exit loop
		System.out.println("");
		valid=false;

		//Check for ship collision, ship on the same coordinate

		for (int i = 0; i < fields.length; i++) {

			System.out.println("");
			for (int j = 0; j < fields.length; j++) {

				int x=0;
				while(x<playerShipsCoordinates.length) // read list of coordinates of player
				{
					if(fields[i][j].equals(playerShipsCoordinates[x]))
					{
						int y=0;
						while(y<computerShipsCoordinates.length) // read list of coordinates of computer
						{
							if(fields[i][j].equals(computerShipsCoordinates[y]))
							{
								if(!fields[i][j].equals("@@"))
								{
									fields[i][j]="@@";
									playerShipsCoordinates[y]="*";
									computerShipsCoordinates[y]="*";
									playerShips--;
									computerShips--;
									break;
								}
							}
							y++;
						}
					}
					x++;
				}
			}	
		}

		if(playerShips<maxShipsPlayer)
		{
			System.out.println("Ships collisions with computer");
		}
		else
		{
			System.out.println("No ships collisions with computer");
		}

		printFieldPlay(fields, playerShipsCoordinates);
		System.out.println("\nPlayer has "+playerShips+" ships left");
		System.out.println("Computer has "+computerShips+" ships left");

		// SHOOTING
		while(playerShips != 0 && computerShips !=0)
		{
			valid=false;
			//Player shoots
			while(!valid) //check userinput
			{
				try
				{
					System.out.println("Enter coordinate to shoot: "); //user input
					inputCoordinate=input.nextLine().toLowerCase().toString();
					sb.append(inputCoordinate); // StringBuilder to splits up letter coordinate for input validation
					checkLetterExist=sb.charAt(0); //get coordinate letter
					checkNumberExist=Integer.parseInt(inputCoordinate.replaceAll("[^0-9]", "")); //get coordinate number
					sb.setLength(0); //clear StringBuilder
					int index=0; //index for validation loop
					boolean hit = false;
					while(index<fields.length && checkNumberExist <=fields.length && checkNumberExist>0) //validate user input
					{

						if(checkLetterExist == letterCoordinates[index].charAt(0)) //first input letter must be the same as in the letter coordinates list
						{
							valid=true;
							int i=0;

							while(i<computerShipsCoordinates.length)
							{
								if(computerShipsCoordinates[i].equals(inputCoordinate))
								{
									System.out.println("HIT ON "+computerShipsCoordinates[i].toUpperCase());
									computerShipsCoordinates[i]="*";
									hit=true;
									computerShips--;

									for (int y = 0; y < fields.length; y++)
									{
										System.out.println("");
										printFieldLineDivider(fields.length);
										System.out.println("");
										for (int j = 0; j < fields.length; j++) {
											if(j==0)				{
												System.out.print(" | ");
											}
											if(fields[y][j].equals(inputCoordinate))
											{
												fields[y][j]="@@";
												System.out.print(fields[y][j]+" | ");
											}
											else
											{
												System.out.print(fields[y][j]+" | ");
											}
										}
										
									}
									System.out.println("");
									printFieldLineDivider(fields.length);
									System.out.println("\n\nPress enter to continue");
									input.nextLine();
									System.out.println("");
									break;
								}
								i++;
							}
							if(!hit)
							{
								System.out.println("YOU MISSED!");
								break;
							}
						}
						index++;
					}
					if(valid)
					{
						break;
					}
					else
					{	
						System.out.println("Coordinate not in range of field");	
					}

				} catch (Exception e) {
					System.out.println("Enter valid coordinate");
					valid=false;
				}
			}

			//Computer shoots
			System.out.println("Computer turn");
			int randomX=rn.nextInt(boardSize);
			int randomY=(rn.nextInt(boardSize))+1;
			sb.append(letterCoordinates[randomX]);
			sb.append(randomY);
			computerChoice=sb.toString();
			sb.setLength(0);
			int i=0;
			boolean computerHit=false;
			while(i<playerShipsCoordinates.length)
			{
				if(playerShipsCoordinates[i].equals(computerChoice))
				{
					System.out.println("Computer has HIT on "+playerShipsCoordinates[i]);
					playerShipsCoordinates[i]="*";
					computerHit=true;
					playerShips--;
					for (int y = 0; y < fields.length;y++) {
						System.out.println("");
						printFieldLineDivider(fields.length);
						System.out.println("");
						for (int j = 0; j < fields.length; j++) {
							if(j==0)				{
								System.out.print(" | ");
							}
							if(fields[y][j].equals(computerChoice))
							{
								fields[y][j]="@@";
								System.out.print(fields[y][j]+" | ");
							}
							else
							{
								System.out.print(fields[y][j]+" | ");
							}
						}
					}
					System.out.println("");
					printFieldLineDivider(fields.length);
					System.out.println("\n\nPress enter to continue");
					input.nextLine();
					System.out.println("");
					break;
				}
				i++;
			}
			if(!computerHit)
			{
				System.out.println("Computer missed");
			}
			System.out.println("You have "+playerShips+" ships");
			System.out.println("Computer has "+computerShips+" ships\n");

		}

		if(playerShips==0)
		{
			System.out.println("You losed all your ships!");
			return false;
		}
		else
		{
			System.out.println("you defeated all the computer ships");
			return true;
		}
	}

	private static String[] computerShipsCoordinates(String[] letterCoordinates, int boardSize ,int maxShipsPlayer) {
		String[] shipCoordinatesArr = new String[maxShipsPlayer];
		StringBuilder sb = new StringBuilder();
		Random rn = new Random();
		String computerChoice;
		boolean duplicateField;
		int randomX;
		int randomY;
		for(int i=0;i<maxShipsPlayer;i++)
		{
			do
			{
				duplicateField=false;
				randomX=rn.nextInt(boardSize);
				randomY=(rn.nextInt(boardSize))+1;
				sb.append(letterCoordinates[randomX]);
				sb.append(randomY);
				computerChoice=sb.toString();
				sb.setLength(0);

				if(i!=0)
				{
					int x=0;
					while(x<i)
					{
						if(shipCoordinatesArr[x].equals(computerChoice))
						{

							System.out.println("Computer chosed duplicate");
							duplicateField=true;
							break;
						}
						x++;
					}
				}
				else
				{
					duplicateField=false;
				}
				if(!duplicateField)
				{
					shipCoordinatesArr[i]=computerChoice;
					System.out.println("Computer deployed ship number "+(i+1));
				}
			}
			while(duplicateField);
		}
		return shipCoordinatesArr;
	}

	private static String[] playerShips(String[] letterCoordinates, int boardSize,int maxShipsPlayer) {
		String inputCoordinate;
		Scanner input = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		String[] shipCoordinatesArr = new String[maxShipsPlayer];
		char checkLetterExist;
		int checkNumberExist;
		boolean duplicateField=false;
		boolean valid; // exit loop
		System.out.println("");
		for(int i=0;i<maxShipsPlayer;i++)
		{
			valid=false;
			while(!valid) //check userinput
			{
				try
				{
					System.out.print("\nEnter coordinate for ship " +(i+1)+" of "+maxShipsPlayer+": "); //user input
					inputCoordinate=input.nextLine().toLowerCase().toString();
					sb.append(inputCoordinate); // StringBuilder to splits up letter coordinate for input validation
					checkLetterExist=sb.charAt(0); //get coordinate letter
					checkNumberExist=Integer.parseInt(inputCoordinate.replaceAll("[^0-9]", "")); //get coordinate number
					sb.setLength(0); //clear StringBuilder
					int j=0; //index for validation loop
					while(j<letterCoordinates.length && checkNumberExist <=boardSize && checkNumberExist>0) //validate user input
					{
						if(checkLetterExist == letterCoordinates[j].charAt(0)) //first input letter must be the same as in the letter coordinates list
						{
							int x=0; //index for duplicate check loop
							while(x<i)//check for duplicate fields already entered
							{
								if(inputCoordinate.equals(shipCoordinatesArr[x]))
								{
									System.out.println("Field already assigned, enter another field");
									duplicateField=true;
									break;
								}
								x++;
							}
							if(!duplicateField)
							{
								shipCoordinatesArr[i]=inputCoordinate;
								valid=true;
								duplicateField=false;
								System.out.println("Ship "+(i+1)+" deployed on field "+shipCoordinatesArr[i]);
								break;
							}	
						}
						j++;
					}
					if(valid)
					{
						break;
					}
					else
					{
						if(!duplicateField)
						{
							System.out.println("Coordinate not in range of field");
						}
					}
					duplicateField=false;
				} catch (NumberFormatException nfe) {
					System.out.println("Enter valid coordinat");
					valid=false;
				}
			}
		}
		System.out.println("");
		return shipCoordinatesArr;
	}

	private static void printFieldLineDivider(int length)
	{
		System.out.print(" ");
		for (int i=0;i<length;i++)
		{
			if(i<9) {
				System.out.print("-----");
			}
			else {
				System.out.print("------");
			}
		}
	}

	private static void printFieldArr(String[][]field) {
		for (int i = 0; i < field.length; i++) {

			printFieldLineDivider(field.length);
			System.out.println("");
			for (int j = 0; j < field.length; j++) {
				if(j==0)				{
					System.out.print(" | ");
				}
				System.out.print(field[i][j]+" | ");
			}
			System.out.println("");
		}
		printFieldLineDivider(field.length);
	}

	public static String[][] createFieldArr(int size,String[] letterCoordinates)
	{
		String[][] square = new String[size][size];
		StringBuilder sb = new StringBuilder();
		String field;
		for(int i=0;i<square.length;i++)
		{
			for(int j=0;j<square.length;j++)
			{
				sb.append(letterCoordinates[i]+""+(j+1));
				square[i][j]=sb.toString();
				sb.setLength(0);
			}
		}
		return square;
	}

	public static String[] createLetterCoordinatesArr()
	{
		String[] letters = new String[26];
		for(char ch ='a';ch<='z';ch++)
		{
			letters[ch-'a']=String.valueOf(ch);

		}
		return letters;
	}

	public static int boardSize()
	{
		int boardSize=0;
		int maxBoardSize=10; // MAX SET = 25 due alphabet array coordinates
		int minBoardSize=3;
		boolean invalid= true;
		Scanner input = new Scanner(System.in);
		System.out.println("Enter board size ["+minBoardSize+"-"+maxBoardSize+"]: ");

		do
		{
			// Checks that the input can be parsed as an int
			if(input.hasNextInt())
			{	
				boardSize=input.nextInt();
				input.nextLine();  // Advances the scanner to prevent input errors
				if(boardSize <= maxBoardSize && boardSize >= minBoardSize)
				{
					invalid=false;  // Sets the condition to false to break the loop
				}
				else
				{
					System.out.println("Size between "+ minBoardSize +" and "+maxBoardSize);
				}
			}
			else
			{
				System.out.println("Invalid input");
				input.nextLine(); // Advances the scanner to prevent input errors
			}
		}
		while(invalid); // The loop ends when the input is valid
		return boardSize;
	}
}