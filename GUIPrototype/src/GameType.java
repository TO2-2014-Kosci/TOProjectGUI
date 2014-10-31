
public enum GameType {
	POKER,
	NPLUS,
	NMULTI;
	
	public String toString() {
		switch (this) {
		case POKER: return "Poker";
		case NPLUS: return "N+";
		case NMULTI: return "N*";
		default: return "";
		}
	}
}
