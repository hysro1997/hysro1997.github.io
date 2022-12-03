// Generated from D:/Java/workspaces/stellaris-config-parser\Stellaris.g4 by ANTLR 4.9.1
package net.turanar.stellaris.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link StellarisParser}.
 */
public interface StellarisListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link StellarisParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(StellarisParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link StellarisParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(StellarisParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link StellarisParser#map}.
	 * @param ctx the parse tree
	 */
	void enterMap(StellarisParser.MapContext ctx);
	/**
	 * Exit a parse tree produced by {@link StellarisParser#map}.
	 * @param ctx the parse tree
	 */
	void exitMap(StellarisParser.MapContext ctx);
	/**
	 * Enter a parse tree produced by {@link StellarisParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterPair(StellarisParser.PairContext ctx);
	/**
	 * Exit a parse tree produced by {@link StellarisParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitPair(StellarisParser.PairContext ctx);
	/**
	 * Enter a parse tree produced by {@link StellarisParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(StellarisParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link StellarisParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(StellarisParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link StellarisParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(StellarisParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link StellarisParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(StellarisParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link StellarisParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(StellarisParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link StellarisParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(StellarisParser.ValueContext ctx);
}