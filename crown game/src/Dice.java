public class Dice {

	private DiceValue value;

	public Dice() {
		value = DiceValue.getRandom();
	}

	public DiceValue getValue() {
		return value;
	}

	public DiceValue roll() {
		return DiceValue.getRandom();
	}

	// Bug 004: Method added to update dice value after die is rolled in each
	// game turn
	public void setValue(DiceValue d) {
		value = d;
	}

	public String toString() {
		return value.toString();
	}
}
