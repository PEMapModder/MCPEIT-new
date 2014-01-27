/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js_DEPRECATED;


public final class JSR{
	public final static class Exceptions{
		public final static class IDs{
			public final static int EXTRA_INFO=-0x01;
			//syntax errors
			public final static int INVALID_TOKEN_GENERIC=0x20;
			public final static int NOT_CLOSED_GENERIC=0x10;
			public final static int BRACE_NOT_CLOSED=0x11;
			public final static int BRACKET_NOT_CLOSED=0x12;
			public final static int SQ_BRACKET_NOT_CLOSED=0x13;
			public final static int QUOTE_NOT_CLOSED=0x18;
			public final static int DBL_QUOTE_NOT_CLOSED=0x19;
			public final static int SLASH_STAR_COMMENT_NOT_CLOSED=0x1a;
			//compilation errors
			public static final int TRY_BLOCK_NOT_FINALIZED = 0x31;
		}
		public final static class ToStrings{
			public static String get(int key){
				switch (key) {
				case IDs.EXTRA_INFO:
					return "Extra info - ";
				case IDs.INVALID_TOKEN_GENERIC:
					return "Unexpected token";
				case IDs.NOT_CLOSED_GENERIC:
					return "Bracket not closed";
				case IDs.BRACE_NOT_CLOSED:
					return "Unexpected end of script; \"}\" expected";
				case IDs.BRACKET_NOT_CLOSED:
					return "Unexpected end of script; \")\" expected";
				case IDs.SQ_BRACKET_NOT_CLOSED:
					return "Unexpected end of script; \"]\" expected";
				case IDs.QUOTE_NOT_CLOSED:
					return "Unexpected end of script; \"\'\" expected";
				case IDs.DBL_QUOTE_NOT_CLOSED:
					return "Unexpected end of script; \"\"\" expected";
				case IDs.SLASH_STAR_COMMENT_NOT_CLOSED:
					return "Unexpected end of script; \"*/\" expected";
				case IDs.TRY_BLOCK_NOT_FINALIZED:
					return "Missing try block ExceptionCatcher or ExceptionFinalizer";
				default:
					return "Exception";
				}
			}
		}
	}
	public final static class Tokens{
		
	}
}
