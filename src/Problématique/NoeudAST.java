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
  public NoeudAST(Terminal terminal, ElemAST rightChild, ElemAST leftChild) {
    this.terminal = terminal;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }

 
  /** Evaluation de noeud d'AST
   */
  public String EvalAST( ) {
    try{
      return switch (terminal.chaine) {
        case "+" -> String.valueOf(Integer.parseInt(rightChild.EvalAST()) + Integer.parseInt(leftChild.EvalAST()));
        case "-" -> String.valueOf(Integer.parseInt(rightChild.EvalAST()) - Integer.parseInt(leftChild.EvalAST()));
        case "*" -> String.valueOf(Integer.parseInt(rightChild.EvalAST()) * Integer.parseInt(leftChild.EvalAST()));
        case "/" -> String.valueOf(Integer.parseInt(rightChild.EvalAST()) / Integer.parseInt(leftChild.EvalAST()));
        default -> "-1";};
    }
    catch(Exception e){
      return switch (terminal.chaine) {
        case "+" -> rightChild.EvalAST() + " + " + leftChild.EvalAST();
        case "-" -> rightChild.EvalAST() + " - " + leftChild.EvalAST();
        case "*" -> rightChild.EvalAST() + " * " + leftChild.EvalAST();
        case "/" -> rightChild.EvalAST() + " / " +  leftChild.EvalAST();
        default -> "-1";};
    }

  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
     return rightChild.LectAST() + " " + terminal.chaine +  " " + leftChild.LectAST();
  }

}


