package Problématique;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

  public AnalLex lexical;
  public Terminal dernierTerminal;

/** Constructeur de DescenteRecursive :
      - recoit en argument le nom du fichier contenant l'expression a analyser
      - pour l'initalisation d'attribut(s)
 */
public DescenteRecursive(String in) {
  Reader r = new Reader(in);
  lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical
}


/** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
 *    Elle retourne une reference sur la racine de l'AST construit
 */
public ElemAST AnalSynt( ) {
  dernierTerminal = lexical.prochainTerminal();
  ElemAST n = A();
  if (!dernierTerminal.chaine.equals("")){
    ErreurSynt("traitement de la chaine incomplet");
  }
  return n;
}

  private ElemAST A() {
    ElemAST n1 = null;
    if (dernierTerminal.chaine.equals("(") || !dernierTerminal.chaine.matches("[+\\-()*/]")) {
      n1 = B();
      CheckSuiv("B");
    } else {
      ErreurSynt("Erreur Pr('" + dernierTerminal.chaine + "')");
    }
    Terminal op = null;
    if (dernierTerminal.chaine.equals("+") || dernierTerminal.chaine.equals("-")) {
      op = dernierTerminal;
      dernierTerminal = lexical.prochainTerminal();
      ElemAST n2 = A();
      CheckSuiv("A");
      n1 = new NoeudAST(op, n1, n2);
    }
    return n1;
  }

  private ElemAST B() {
    ElemAST n1 = null;
    if (dernierTerminal.chaine.equals("(") || !dernierTerminal.chaine.matches("[+\\-()*/]")) {
      n1 = C();
      CheckSuiv("C");
    } else {
      ErreurSynt("Erreur Pr('" + dernierTerminal.chaine + "')");
    }
    Terminal op = null;
    if (dernierTerminal.chaine.equals("*") || dernierTerminal.chaine.equals("/")) {
      op = dernierTerminal;
      dernierTerminal = lexical.prochainTerminal();
      ElemAST n2 = B();
      CheckSuiv("B");
      n1 = new NoeudAST(op, n1, n2);
    }
    return n1;
  }

  private ElemAST C() {
    ElemAST n = null;
    if (!dernierTerminal.chaine.matches("[+\\-()*/]")) {
      n = new FeuilleAST(dernierTerminal);
      dernierTerminal = lexical.prochainTerminal();
    } else if (dernierTerminal.chaine.equals("(")) {
      dernierTerminal = lexical.prochainTerminal();
      n = A();
      CheckSuiv("A");
      if (!dernierTerminal.chaine.equals(")")) {
        ErreurSynt("Manque une parenthèse");
      }
      dernierTerminal = lexical.prochainTerminal();
    } else {
      ErreurSynt("Syntaxe incorrecte. Terminal fautif: " + dernierTerminal.chaine + " index: " + lexical.ptrVect);
    }
    return n;
  }

private void CheckSuiv(String rule){
  if(rule.equals("A")){
    if(!(dernierTerminal.chaine.equals(")") || dernierTerminal.chaine.equals(""))){
      ErreurSynt(rule + " Erreur Suiv('" + dernierTerminal.chaine + "')");
    }
  }
  if(rule.equals("B")){
    if(!(dernierTerminal.chaine.equals(")") || dernierTerminal.chaine.equals("+") || dernierTerminal.chaine.equals("-") || dernierTerminal.chaine.equals(""))){
      ErreurSynt(rule + " Erreur Suiv('" + dernierTerminal.chaine + "')");
    }
  }
  if(rule.equals("C")){
    if(!(dernierTerminal.chaine.equals(")") || dernierTerminal.chaine.equals("+") || dernierTerminal.chaine.equals("-") || dernierTerminal.chaine.equals("*") || dernierTerminal.chaine.equals("/") || dernierTerminal.chaine.equals(""))){
      ErreurSynt(rule + " Erreur Suiv('" + dernierTerminal.chaine + "')");
    }
  }
}



/** ErreurSynt() envoie un message d'erreur syntaxique
 */
public void ErreurSynt(String s)
{
    throw new UnknownError(s);
}



  //Methode principale a lancer pour tester l'analyseur syntaxique 
  public static void main(String[] args) {
    String toWriteLect = "";
    String toWritePost = "";
    String toWriteEval = "";

    System.out.println("Debut d'analyse syntaxique");
    if (args.length == 0){
      args = new String [2];
      args[0] = "ExpArith.txt";
      args[1] = "ResultatSyntaxique.txt";
    }
    DescenteRecursive dr = new DescenteRecursive(args[0]);
    try {
      ElemAST RacineAST = dr.AnalSynt();
      toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
      System.out.println(toWriteLect);
      toWritePost += "Lecture de l'AST PostFix trouve : " + RacineAST.PostFix() + "\n";
      System.out.println(toWritePost);
      toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
      System.out.println(toWriteEval);
      Writer w = new Writer(args[1],toWriteLect+toWritePost+toWriteEval); // Ecriture de toWrite dans fichier args[1]

    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      System.exit(51);
    }
    System.out.println("Analyse syntaxique terminee");
  }

}

