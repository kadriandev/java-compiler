class Token {

  public final static int ERROR = 0;
  public final static int IF = 1;
  public final static int THEN = 2;
  public final static int ELSE = 3;
  public final static int END = 4;
  public final static int REPEAT = 5;
  public final static int UNTIL = 6;
  public final static int READ = 7;
  public final static int WRITE = 8;
  public final static int ASSIGN = 9;
  public final static int EQ = 10;
  public final static int LT = 11;
  public final static int GT = 12;
  public final static int PLUS = 13;
  public final static int MINUS = 14;
  public final static int TIMES = 15;
  public final static int OVER = 16;
  public final static int LPAREN = 17;
  public final static int RPAREN = 18;
  public final static int SEMI = 19;
  public final static int ID = 20;
  public final static int NUM = 21;

  public int m_type;
  public String m_value;
  public int m_line;
  public int m_column;
  
  Token (int type, String value, int line, int column) {
    m_type = type;
    m_value = value;
    m_line = line;
    m_column = column;
  }

  public String toString() {
    switch (m_type) {
      case IF:
        return "IF";
      case THEN:
        return "THEN";
      case ELSE:
        return "ELSE";
      case END:
        return "END";
      case REPEAT:
        return "REPEAT";
      case UNTIL:
        return "UNTIL";
      case READ:
        return "READ";
      case WRITE:
        return "WRITE";
      case ASSIGN:
        return "ASSIGN";
      case EQ:
        return "EQ";
      case LT:
        return "LT";
      case GT:
        return "GT";
      case PLUS:
        return "PLUS";
      case MINUS:
        return "MINUS";
      case TIMES:
        return "TIMES";
      case OVER:
        return "OVER";
      case LPAREN:
        return "LPAREN";
      case RPAREN:
        return "RPAREN";
      case SEMI:
        return "SEMI";
      case ID:
        return "ID(" + m_value + ")";
      case NUM:
        return "NUM(" + m_value + ")";
      case ERROR:
        return "ERROR(" + m_value + ")";
      default:
        return "UNKNOWN(" + m_value + ")";
    }
  }
}

