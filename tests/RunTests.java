package tests;

class RunTests {
    public static void main(String[] args) {
        System.out.println("=== Running TestLoadVenues ===");
        TestLoadVenues.main(args);

        System.out.println("\n=== Running TestLoadUsers ===");
        TestLoadUsers.main(args);

        System.out.println("\n=== Running TestLoadEvents ===");
        TestLoadEvents.main(args);

        System.out.println("\n=== All Tests Completed ===");
    }
}