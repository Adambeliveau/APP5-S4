package Problématique;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  public Terminal terminal;
  public ElemAST leftChild;
  public ElemAST rightChild;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(Terminal terminal, ElemAST leftChild, ElemAST rightChild) {
    this.terminal = terminal;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }

 
  /** Evaluation de noeud d'AST
   */
  public String EvalAST( ) {
    try{
      return switch (terminal.chaine) {
        case "+" -> String.valueOf(Integer.parseInt(leftChild.EvalAST()) + Integer.parseInt(rightChild.EvalAST()));
        case "-" -> String.valueOf(Integer.parseInt(leftChild.EvalAST()) - Integer.parseInt(rightChild.EvalAST()));
        case "*" -> String.valueOf(Integer.parseInt(leftChild.EvalAST()) * Integer.parseInt(rightChild.EvalAST()));
        case "/" -> String.valueOf(Integer.parseInt(leftChild.EvalAST()) / Integer.parseInt(rightChild.EvalAST()));
        default -> "-1";};
    }
    catch(Exception e){
      return switch (terminal.chaine) {
        case "+" -> leftChild.EvalAST() + " + " + rightChild.EvalAST();
        case "-" -> leftChild.EvalAST() + " - " + rightChild.EvalAST();
        case "*" -> leftChild.EvalAST() + " * " + rightChild.EvalAST();
        case "/" -> leftChild.EvalAST() + " / " + rightChild.EvalAST();
        default -> "-1";};
    }

  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
      return "(" + leftChild.LectAST() + " " + terminal.chaine +  " " + rightChild.LectAST() + ")";
  }

  public String PostFix(){
      return leftChild.PostFix() +  " " + rightChild.PostFix() + " " + terminal.chaine;
  }
}


