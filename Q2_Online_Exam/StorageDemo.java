
import java.io.IOException;

class ExamException extends Exception {
    ExamException(String message, Throwable cause) {
        super(message, cause);
    }
}

class SubmissionStorageException extends ExamException {
    SubmissionStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

class Storage {
    void save(String fileName, long sizeKb)
            throws SubmissionStorageException {
        try {
            if (sizeKb > 500) {
                throw new IOException("Disk quota exceeded");
            }
            System.out.println("Saved " + fileName);
        } catch (IOException e) {
            throw new SubmissionStorageException(
                "Unable to save submission", e);
        }
    }
}

public class StorageDemo {
    public static void main(String[] args) {
        Storage storage = new Storage();

        try {
            storage.save("answer01.pdf", 700);
        } catch (SubmissionStorageException e) {
            System.out.println("Submission failed: " + e.getMessage());
            System.out.println("Original cause: " + e.getCause());
            e.printStackTrace();
        }
    }
}
