// Generated from D:/Java/workspaces/stellaris-config-parser\Stellaris.g4 by ANTLR 4.9.1
package net.turanar.stellaris.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link StellarisParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface StellarisVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link StellarisParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(StellarisParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link StellarisParser#map}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMap(StellarisParser.MapContext ctx);
	/**
	 * Visit a parse tree produced by {@link StellarisParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair(StellarisParser.PairContext ctx);
	/**
	 * Visit a parse tree produced by {@link StellarisParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(StellarisParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link StellarisParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(StellarisParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link StellarisParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(StellarisParser.ValueContext ctx);
}