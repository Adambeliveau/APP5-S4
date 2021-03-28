package Problématique;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

    public Terminal terminal;


/**Constructeur pour l'initialisation d'attribut(s)
 */
  public FeuilleAST(Terminal terminal) {  // avec arguments
    this.terminal = terminal;
  }


  /** Evaluation de feuille d'AST
   */
  public String EvalAST( ) {
    return LectAST();
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
    return terminal.chaine;
  }

  public String PostFix() { return terminal.chaine; }

}
