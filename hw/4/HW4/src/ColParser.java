public class ColParser {
	
	private static final String SKIP_REGEX = ".*\\?.*";
	private static final String NUM_REGEX = ".*[<>\\$].*";
	private static final String GOAL_REGEX = ".*[<>!].*";
	private static final String LESS_REGEX = ".*[<].*";

	public static ColMetadata parse(String name) {
		ColMetadata metadata = new ColMetadata();
		metadata.isSkip = name.matches(SKIP_REGEX);
		metadata.isNum = name.matches(NUM_REGEX);
		metadata.isGoal = name.matches(GOAL_REGEX);
		metadata.isLess = name.matches(LESS_REGEX);
		return metadata;
	}

}
