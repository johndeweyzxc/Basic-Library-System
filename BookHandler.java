import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

// Class for handling the books such as reading, creating or deleting a book.
public class BookHandler {

    // Method for retrieving all the books this is use when retrieving a single
    // book. The method opens the file AvailableBooks.books and reads the data.
    public List<String> retrieveBooks() throws FileNotFoundException {
        // You need to specify the exact path of the AvailableBooks.books file
        File descriptorBooksFile = new File(
                "C:\\Users\\Exact Path\\Data\\AvailableBooks.books");
        Scanner readBooksFile = new Scanner(descriptorBooksFile);

        List<String> listOfBooks = new ArrayList<>();

        while (readBooksFile.hasNextLine()) {
            String book = readBooksFile.nextLine();
            listOfBooks.add(book);
        }

        readBooksFile.close();
        return listOfBooks;
    }

    // Method for retrieving a single book
    public void readBooks(List<Integer> indexOfBooks) throws FileNotFoundException {
        BookHandler bookHandler = new BookHandler();
        List<String> bookToRead = new ArrayList<>();
        bookToRead = bookHandler.retrieveBooks();

        System.out.println("\nYou are now reading the following books:");

        // To retrieve the title and the description of the book, the data is split.
        // TitleOfTheBook_DescriptionOfTheBook = [TitleOfTheBook, DescriptionOfTheBook]
        for (int i = 0; i < indexOfBooks.size(); i++) {
            int listOfBooks = indexOfBooks.get(i);
            String[] bookInfo = bookToRead.get(listOfBooks).split("_");
            String bookName = bookInfo[0];
            String bookDescription = bookInfo[1];

            System.out.println(String.format("Name: %s", bookName));
            System.out.println(String.format("Description: %s\n", bookDescription));
        }
    }

    // Method for updating the books this is use when creating a book or deleting a
    // book.
    private void updateBooks(List<String> updatedBooks) throws IOException {
        String concatBooks = "";
        // You need to specify the exact path of the AvailableBooks.books file
        FileWriter writeUpdateBooks = new FileWriter(
                "C:\\Users\\Exact Path\\Data\\AvailableBooks.books");

        for (int i = 0; i < updatedBooks.size(); i++) {
            // Everybook is seperated with \n which is a new line.
            concatBooks = concatBooks.concat(updatedBooks.get(i) + "\n");
        }

        writeUpdateBooks.write(concatBooks);
        writeUpdateBooks.close();
    }

    // Method for creating or publishing a book
    public void createBooks(List<String> indexOfBooks) throws IOException {
        BookHandler bookHandler = new BookHandler();
        List<String> booksToAdd = new ArrayList<>();
        booksToAdd.addAll(bookHandler.retrieveBooks());

        System.out.println("\nYou are now adding the following books to available books: ");
        for (int i = 0; i < indexOfBooks.size(); i++) {
            String[] bookInfo = indexOfBooks.get(i).split("_");
            String bookName = bookInfo[0];
            System.out.println(String.format("Name: %s", bookName));
            booksToAdd.add(indexOfBooks.get(i));
        }

        updateBooks(booksToAdd);
    }

    // Method for deleting a book
    public void deleteBooks(List<Integer> indexOfBooks, String deleteBorrow, List<Integer> borrowTime)
            throws IOException {
        BookHandler bookHandler = new BookHandler();
        List<String> bookToDelete = new ArrayList<>();

        bookToDelete = bookHandler.retrieveBooks();
        String deletingBooks = "";

        if (deleteBorrow == "borrow") {
            deletingBooks = "You are now borrowing the following books:";
        } else if (deleteBorrow == "delete") {
            deletingBooks = "You are now deleting the following books:";
        }

        System.out.println("\n" + deletingBooks);

        for (int i = 0; i < indexOfBooks.size(); i++) {
            int listOfBooks = indexOfBooks.get(i);
            String[] bookInfo = bookToDelete.get(listOfBooks).split("_");
            String bookName = bookInfo[0];

            if (listOfBooks > bookToDelete.size()) {
                continue;
            } else {
                int bookDelete = indexOfBooks.get(i);
                if (borrowTime.size() > 0) {
                    int timeToBorrow = borrowTime.get(i);
                    System.out.println(String.format("Name: %s\nBorrowing for: %d hours\n", bookName, timeToBorrow));
                } else if (borrowTime.size() == 0) {
                    System.out.println(String.format("Name: %s", bookName));
                }
                bookToDelete.remove(bookDelete);
            }
        }

        updateBooks(bookToDelete);
    }
}
