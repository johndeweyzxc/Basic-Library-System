import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class App {

    private List<Integer> borrowFees = new ArrayList<>();

    private List<Integer> returnBorrowFees() {
        return borrowFees;
    }

    private BufferedReader inputReader() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        return bufferedReader;
    }

    private BookHandler handleBooks() {
        BookHandler bookHandler = new BookHandler();
        return bookHandler;
    }

    // Welcome display, this is the first thing you will see in the console.
    public void welcomeDisplay() {
        System.out.println();
        System.out.println("    ******************************************* ");
        System.out.println("  *                                             *");
        System.out.println("*   ***** Welcome to Basic Library System *****   *");
        System.out.println("  *                                             *");
        System.out.println("    ******************************************* \n");
    }

    // Printing all the available books in the console.
    public void showAllBooks(List<String> iterateToBooks) throws FileNotFoundException {

        System.out.println("Available books: ");
        for (int i = 0; i < iterateToBooks.size(); i++) {
            // Split the data using underscore
            // TitleOfTheBook_DescriptionOfTheBook = [TitleOfTheBook, DescriptionOfTheBook]
            String[] bookInfo = iterateToBooks.get(i).split("_");
            String bookName = bookInfo[0];

            System.out.println(String.format("[%d] %s", i, bookName));
        }
    }

    // This method ask the user if he/she wants to delete or read a book.
    private List<Integer> readDeleteBooks(String mode) throws NumberFormatException, IOException {
        List<String> initialBooks = new ArrayList<>();
        List<Integer> selectedBooks = new ArrayList<>();

        initialBooks = handleBooks().retrieveBooks();
        int stopSelecting = 1;
        String bookSelect = "";
        String bookSelectMore = "";
        String inputPrompt = ">>>";

        int selectedBook;

        if (mode == "read") {
            bookSelect = "Which book you want to read? Use the index for accessing";
            bookSelectMore = "Do you want to add more books to read? [1 - YES/0 - NO]";
        } else if (mode == "borrow") {
            bookSelect = "Which book you want to borrow? Use the index for accessing";
            bookSelectMore = "Do you want to borrow more books? [1 - YES/0 - NO]";
        } else if (mode == "delete") {
            bookSelect = "Which book you want to delete? Use the index for accessing";
            bookSelectMore = "Do you want to delete more books? [1 - YES/0 - NO]";
        }

        do {
            System.out.println(bookSelect);
            System.out.print(inputPrompt);
            selectedBook = Integer.parseInt(inputReader().readLine());
            selectedBooks.add(selectedBook);

            if (mode == "borrow" || mode == "delete") {
                if (mode == "borrow") {
                    System.out.println(
                            "\nChoose a price plan, use index for accessing:\n[0] 1-3 DAYS: PHP15\n[1] 4-6 DAYS: PHP20\n[2] 7 DAYS ABOVE: PHP50");

                    System.out.print(inputPrompt);
                    int feeInput;
                    feeInput = Integer.parseInt(inputReader().readLine());

                    switch (feeInput) {
                        case 0:
                            this.borrowFees.add(3 * 24);
                            break;
                        case 1:
                            this.borrowFees.add(6 * 24);
                            break;
                        case 2:
                            this.borrowFees.add(7 * 24);
                            break;
                    }
                }
                initialBooks.remove(selectedBook);
                showAllBooks(initialBooks);
            }

            // Ask user if he/she wants to delete, borrow, or read more books.
            System.out.println(bookSelectMore);
            System.out.print(inputPrompt);
            stopSelecting = Integer.parseInt(inputReader().readLine());
        } while (stopSelecting == 1);

        return selectedBooks;
    }

    // Method for creating a new book. The user will supply the description and the
    // name of the book.
    private List<String> addBooks() throws IOException {
        List<String> addedBooks = new ArrayList<>();
        String bookName = "Enter the name of the book you want to add";
        String bookDescription = "Enter the decription of the book";
        String addMore = "Do you want to add more books? [1 - YES/0 - NO]";
        String inputPrompt = ">>>";
        int addMoreBooks = 1;

        do {
            String newName;
            String newDescription;
            String newBook;

            System.out.println(bookName);
            System.out.print(inputPrompt);
            newName = inputReader().readLine();

            System.out.println(bookDescription);
            System.out.print(inputPrompt);
            newDescription = inputReader().readLine();

            newBook = newName + "_" + newDescription;
            addedBooks.add(newBook);

            // Ask the user if he/she wants to add more book.
            System.out.println(addMore);
            System.out.print(inputPrompt);
            addMoreBooks = Integer.parseInt(inputReader().readLine());
        } while (addMoreBooks == 1);

        return addedBooks;
    }

    // This is the employee method, this method is use if the user is an employee of
    // the library. The employee can do everything in the library such as borrow,
    // read, add, or delete a book.
    public void employee() throws NumberFormatException, IOException {
        int employeeInput;

        System.out.println();
        System.out.println(
                "What can you do with the books [3 - BORROW BOOKS/2 - READ BOOKS/1 - ADD OR DELETE BOOKS/0 - EXIT]");
        System.out.print(">>>");
        employeeInput = Integer.parseInt(inputReader().readLine());

        if (employeeInput == 3) {
            List<Integer> booksToBorrow = new ArrayList<>();
            booksToBorrow = readDeleteBooks("borrow");
            handleBooks().deleteBooks(booksToBorrow, "borrow", returnBorrowFees());
        } else if (employeeInput == 2) {
            List<Integer> booksToRead = new ArrayList<>();
            booksToRead = readDeleteBooks("read");
            handleBooks().readBooks(booksToRead);
        } else if (employeeInput == 1) {
            System.out.println("Do you want to add or delete? [1 - ADD/0 - DELETE]");
            System.out.print(">>>");
            int userChoice = Integer.parseInt(inputReader().readLine());

            if (userChoice == 1) {
                List<String> booksToAdd = new ArrayList<>();
                booksToAdd = addBooks();
                handleBooks().createBooks(booksToAdd);
            } else if (userChoice == 0) {
                List<Integer> booksToDelete = new ArrayList<>();
                booksToDelete = readDeleteBooks("delete");
                handleBooks().deleteBooks(booksToDelete, "delete", returnBorrowFees());
            }
        } else {
            System.exit(0);
        }
    }

    // This is the student method. This method is use if the user is a student. The
    // student can only read or borrow a book.
    public void student() throws NumberFormatException, IOException {
        int studentInput;

        System.out.println();
        System.out.println("What can you do with the books [2 - BORROW BOOKS/1 - READ BOOKS/0 - EXIT]");
        System.out.print(">>>");
        studentInput = Integer.parseInt(inputReader().readLine());

        if (studentInput == 2) {
            List<Integer> booksToBorrow = new ArrayList<>();
            booksToBorrow = readDeleteBooks("borrow");
            handleBooks().deleteBooks(booksToBorrow, "borrow", returnBorrowFees());
        } else if (studentInput == 1) {
            List<Integer> booksToRead = new ArrayList<>();
            booksToRead = readDeleteBooks("read");
            handleBooks().readBooks(booksToRead);
        } else {
            System.exit(0);
        }
    }

    // Method for determining if the user is an employee or student. If the user is
    // an employee execute employee method but if the user is a student execute
    // student method.
    public int employeeOrStudent() throws NumberFormatException, IOException {
        int identify;
        System.out.println("Are you an employee or student? [2 - EMPLOYEE/1 - STUDENT/0 - EXIT INSTEAD]");
        System.out.print(">>>");
        identify = Integer.parseInt(inputReader().readLine());

        if (identify == 0) {
            System.exit(0);
        }

        System.out.println();
        return identify;
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        App MainClass = new App();
        int identification;

        MainClass.welcomeDisplay();
        identification = MainClass.employeeOrStudent();
        MainClass.showAllBooks(MainClass.handleBooks().retrieveBooks());

        // If the result is 2 then it is an employee else it is a student.
        if (identification == 2) {
            MainClass.employee();
        } else {
            MainClass.student();
        }
    }
}
