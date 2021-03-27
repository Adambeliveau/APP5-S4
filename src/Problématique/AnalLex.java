package Problématique;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

  public String chaine;
  public int ptrVect;
  public int etat;

	
/** Constructeur pour l'initialisation d'attribut(s)
 */
  public AnalLex(String reader) {  // arguments possibles
    chaine = reader;
    ptrVect = 0;
    etat = 0;
  }


/** resteTerminal() retourne :
      false  si tous les terminaux de l'expression arithmetique ont ete retournes
      true s'il reste encore au moins un terminal qui n'a pas ete retourne 
 */
  public boolean resteTerminal( ) {
    return ptrVect < chaine.length();
  }
  
  
/** prochainTerminal() retourne le prochain terminal
      Cette methode est une implementation d'un AEF
 */  
  public Terminal prochainTerminal( ) {
    String UL = "";
    char currentChar;
    while(resteTerminal()){
      currentChar = chaine.charAt(ptrVect++);
      switch (etat) {
        case 0 : {
          if (String.valueOf(currentChar).matches("[+\\-()*/]")) {
            return new Terminal(String.valueOf(currentChar));
          } else if (String.valueOf(currentChar).matches("[A-Z]")) {
            etat = 1;
            UL += currentChar;
          } else if (String.valueOf(currentChar).matches("[0-9]")) {
            etat = 3;
            UL += currentChar;
          } else {
            ErreurLex("charactère indésirable (" + chaine.charAt(ptrVect - 1) + ") à l'état : " + etat);
          }
          break;
        }
        case 1 : {
          if (String.valueOf(currentChar).matches("[A-Z|a-z]")) {
            UL += currentChar;
          }
          else if (String.valueOf(currentChar).equals("_")){
            if(!resteTerminal()){
              ErreurLex("Caractère de fin('" + currentChar + "') invalide");
            }
            etat = 2;
            UL += currentChar;
          }
          else {
            ptrVect--;
            etat = 0;
            return new Terminal(UL);
          }
          break;
        }
        case 2 : {
          if(String.valueOf(currentChar).equals("_")){
            ErreurLex("Doublons de '_'");
          }
          else if(String.valueOf(currentChar).matches("[A-Z|a-z]")){
            etat = 1;
            UL += currentChar;
          }
          break;
        }
        case 3 : {
          if (String.valueOf(currentChar).matches("[0-9]")) {
            UL += currentChar;
          }
          else {
            ptrVect--;
            etat = 0;
            return new Terminal(UL);
          }
          break;
        }
      }
    }
    return new Terminal(UL);
  }

 
/** ErreurLex() envoie un message d'erreur lexicale
 */ 
  public void ErreurLex(String s) {	
     throw new UnknownError(s);
  }

  
  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    if (args.length == 0){
    args = new String [2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatLexical.txt";
    }
    Reader r = new Reader(args[0]);

    AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

    // Execution de l'analyseur lexical
    Terminal t = null;
    while(lexical.resteTerminal()){
      t = lexical.prochainTerminal();
      toWrite += t.chaine + "\n" ;  // toWrite contient le resultat d'analyse lexicale
    }
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}
