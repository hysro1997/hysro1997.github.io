// Generated from D:/Java/workspaces/stellaris-config-parser\Stellaris.g4 by ANTLR 4.9.1
package net.turanar.stellaris.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class StellarisLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, BOOLEAN=3, VARIABLE=4, SPECIFIER=5, NUMBER=6, DATE=7, 
		BAREWORD=8, STRING=9, WS=10, LINE_COMMENT=11;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "BOOLEAN", "VARIABLE", "SPECIFIER", "NUMBER", "DATE", 
			"BAREWORD", "STRING", "WS", "LINE_COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "BOOLEAN", "VARIABLE", "SPECIFIER", "NUMBER", "DATE", 
			"BAREWORD", "STRING", "WS", "LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public StellarisLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Stellaris.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\r\u0091\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\5\4,\n\4\3\5\3\5\3\5\7\5\61\n\5\f\5\16\5\64\13\5\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6>\n\6\3\7\5\7A\n\7\3\7\6\7D\n\7\r\7\16"+
		"\7E\3\7\3\7\5\7J\n\7\3\7\6\7M\n\7\r\7\16\7N\3\7\3\7\6\7S\n\7\r\7\16\7"+
		"T\3\7\5\7X\n\7\3\7\6\7[\n\7\r\7\16\7\\\5\7_\n\7\3\b\6\bb\n\b\r\b\16\b"+
		"c\3\b\3\b\6\bh\n\b\r\b\16\bi\3\b\3\b\6\bn\n\b\r\b\16\bo\3\t\3\t\7\tt\n"+
		"\t\f\t\16\tw\13\t\3\n\3\n\7\n{\n\n\f\n\16\n~\13\n\3\n\3\n\3\13\6\13\u0083"+
		"\n\13\r\13\16\13\u0084\3\13\3\13\3\f\3\f\7\f\u008b\n\f\f\f\16\f\u008e"+
		"\13\f\3\f\3\f\2\2\r\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r"+
		"\3\2\13\4\2C\\c|\b\2\'\'/\60\62;C\\aac|\4\2>>@@\3\2\62;\b\2##&&C\\^^c"+
		"|~~\7\2##&^aac|~~\3\2$$\5\2\13\f\17\17\"\"\4\2\f\f\17\17\2\u00a8\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\3\31\3"+
		"\2\2\2\5\33\3\2\2\2\7+\3\2\2\2\t-\3\2\2\2\13=\3\2\2\2\r^\3\2\2\2\17a\3"+
		"\2\2\2\21q\3\2\2\2\23x\3\2\2\2\25\u0082\3\2\2\2\27\u0088\3\2\2\2\31\32"+
		"\7}\2\2\32\4\3\2\2\2\33\34\7\177\2\2\34\6\3\2\2\2\35\36\7{\2\2\36\37\7"+
		"g\2\2\37,\7u\2\2 !\7p\2\2!,\7q\2\2\"#\7v\2\2#$\7t\2\2$%\7w\2\2%,\7g\2"+
		"\2&\'\7h\2\2\'(\7c\2\2()\7n\2\2)*\7u\2\2*,\7g\2\2+\35\3\2\2\2+ \3\2\2"+
		"\2+\"\3\2\2\2+&\3\2\2\2,\b\3\2\2\2-.\7B\2\2.\62\t\2\2\2/\61\t\3\2\2\60"+
		"/\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\n\3\2\2\2\64\62"+
		"\3\2\2\2\65>\7?\2\2\66\67\7>\2\2\67>\7@\2\28>\t\4\2\29:\7>\2\2:>\7?\2"+
		"\2;<\7@\2\2<>\7?\2\2=\65\3\2\2\2=\66\3\2\2\2=8\3\2\2\2=9\3\2\2\2=;\3\2"+
		"\2\2>\f\3\2\2\2?A\7/\2\2@?\3\2\2\2@A\3\2\2\2AC\3\2\2\2BD\t\5\2\2CB\3\2"+
		"\2\2DE\3\2\2\2EC\3\2\2\2EF\3\2\2\2FG\3\2\2\2G_\7\'\2\2HJ\7/\2\2IH\3\2"+
		"\2\2IJ\3\2\2\2JL\3\2\2\2KM\t\5\2\2LK\3\2\2\2MN\3\2\2\2NL\3\2\2\2NO\3\2"+
		"\2\2OP\3\2\2\2PR\7\60\2\2QS\t\5\2\2RQ\3\2\2\2ST\3\2\2\2TR\3\2\2\2TU\3"+
		"\2\2\2U_\3\2\2\2VX\7/\2\2WV\3\2\2\2WX\3\2\2\2XZ\3\2\2\2Y[\t\5\2\2ZY\3"+
		"\2\2\2[\\\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]_\3\2\2\2^@\3\2\2\2^I\3\2\2\2"+
		"^W\3\2\2\2_\16\3\2\2\2`b\t\5\2\2a`\3\2\2\2bc\3\2\2\2ca\3\2\2\2cd\3\2\2"+
		"\2de\3\2\2\2eg\7\60\2\2fh\t\5\2\2gf\3\2\2\2hi\3\2\2\2ig\3\2\2\2ij\3\2"+
		"\2\2jk\3\2\2\2km\7\60\2\2ln\t\5\2\2ml\3\2\2\2no\3\2\2\2om\3\2\2\2op\3"+
		"\2\2\2p\20\3\2\2\2qu\t\6\2\2rt\t\7\2\2sr\3\2\2\2tw\3\2\2\2us\3\2\2\2u"+
		"v\3\2\2\2v\22\3\2\2\2wu\3\2\2\2x|\7$\2\2y{\n\b\2\2zy\3\2\2\2{~\3\2\2\2"+
		"|z\3\2\2\2|}\3\2\2\2}\177\3\2\2\2~|\3\2\2\2\177\u0080\7$\2\2\u0080\24"+
		"\3\2\2\2\u0081\u0083\t\t\2\2\u0082\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084"+
		"\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0087\b\13"+
		"\2\2\u0087\26\3\2\2\2\u0088\u008c\7%\2\2\u0089\u008b\n\n\2\2\u008a\u0089"+
		"\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\u008f\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0090\b\f\3\2\u0090\30\3\2\2"+
		"\2\25\2+\62=@EINTW\\^ciou|\u0084\u008c\4\b\2\2\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}