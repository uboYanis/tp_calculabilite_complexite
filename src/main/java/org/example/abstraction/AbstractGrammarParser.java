package org.example.abstraction;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Classe abstraite représentant un parseur de grammaire générique.
 *
 * @param <T> Le type du parseur, qui doit étendre la classe {@link Parser}.
 *     <p>Cette classe fournit une structure de base pour la création de parseurs basés sur ANTLR.
 *     Elle nécessite l'implémentation de méthodes spécifiques pour créer le lexer et le parser
 *     ainsi que pour définir le point de départ du parsing et récupérer la valeur analysée.
 */
public abstract class AbstractGrammarParser<T extends Parser> {

  private final String input;

  /**
   * Constructeur de la classe AbstractGrammarParser.
   *
   * @param input La chaîne d'entrée à analyser.
   */
  protected AbstractGrammarParser(String input) {
    this.input = input;
  }

  /**
   * Crée une instance de {@link Parser} à partir d'un flux de tokens.
   *
   * @param tokens Le flux de tokens à utiliser pour créer le parseur.
   * @return Une instance de {@link Parser} initialisée.
   */
  protected abstract T createParser(CommonTokenStream tokens);

  /**
   * Démarre le parsing en utilisant le parseur fourni.
   *
   * @param parser Le parseur à utiliser pour démarrer l'analyse.
   * @return L'arbre obtenu à partir de l'entrée.
   */
  protected abstract ParseTree startParser(T parser);

  /**
   * Crée un lexer à partir de la chaîne d'entrée.
   *
   * @param input La chaîne d'entrée à analyser.
   * @return Une instance de {@link Lexer} initialisée.
   */
  protected abstract Lexer createLexer(String input);

  /**
   * Récupère la valeur analysée à partir de l'entrée.
   *
   * @return L'objet représentant la valeur analysée.
   */
  protected abstract Object getValue();

  /**
   * Fournit le nom de la grammaire utilisée par le parseur.
   *
   * @return Le nom de la grammaire.
   */
  protected abstract String getGrammarName();

  /**
   * Initialise le parseur en créant un lexer et un flux de tokens.
   *
   * @return Une instance de {@link Parser} initialisée.
   */
  public T initializeParser() {
    Lexer lexer = createLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    return createParser(tokens);
  }

  /**
   * Effectue le parsing de l'entrée et renvoie un indicateur de validité.
   *
   * @return {@code true} si la chaîne est valide selon la grammaire, {@code false} sinon.
   */
  public boolean parse() {
    try {
      T parser = initializeParser();
      ParseTree tree = startParser(parser);
      boolean isValid = parser.getNumberOfSyntaxErrors() == 0;

      logResult(isValid);
      return isValid;

    } catch (Exception e) {
      logResult(false);
      return false;
    }
  }

  /**
   * Journalise le résultat du parsing.
   *
   * @param isValid Indicateur de validité de l'analyse.
   */
  private void logResult(boolean isValid) {
    String message =
        isValid
            ? String.format(
                "✅ La chaîne %s est valide selon la grammaire %s.", input, getGrammarName())
            : String.format(
                "❌ La chaîne %s n'est pas valide selon la grammaire %s.", input, getGrammarName());

    System.out.println(message);
  }
}
