System.out.print("Insert genre:"); 
Scanner userInput = new Scanner(System.in); //reads from standard input        
String genreExample = userInput.next(); //saves input as a string
Genre input = Genre.valueOf(genreExample); //string is matched with corresponding enum
//if there isn't a match we can make a control branch that handles that
//now we can use "input" for when we want to call the genre of the album
