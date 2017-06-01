package file_manager;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class File_manager {
    
    public final static String LIST = "LIST";
    public final static String INFO = "INFO";
    public final static String CREATE = "create_dir";
    public final static String RENAME = "rename";
    public final static String COPY = "copy";
    public final static String MOVE = "move";
    public final static String DELETE = "delete";

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to application File Manager ver.0.1 Alpha.");
        System.out.println("Please enter a command: ");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
            String s = br.readLine();
            switch (s) {

                case LIST:
                    System.out.print("Enter path or directory: ");
                    try (BufferedReader brList = new BufferedReader(new InputStreamReader(System.in));) {
                        String sList = brList.readLine();
                        File path = new File(sList);
                        System.out.println("List of directories: ");
                        if (path.exists() && path.isDirectory()) {
                            File[] elements = path.listFiles();
                            for (File element : elements) {
                                System.out.println(element.getName());
                            }
                        } else {
                            System.out.println("Sorry, your  path or directory doesn't exists!");
                        }
                        break;
                    }

                case INFO:
                    System.out.print("Enter path or directory: ");
                    try (BufferedReader brInfo = new BufferedReader(new InputStreamReader(System.in));) {
                        String sInfo = brInfo.readLine();
                        File file = new File(sInfo);
                        if (file.exists()) {
                            System.out.println("Information about direcories from desired path: ");
                            System.out.println("1. Directory name is " + file.getName());
                            System.out.println("2. Directory path is " + file.getPath());
                            System.out.println("3. Directory size is " + file.length() + " B");

                            Path file1 = Paths.get(sInfo);
                            BasicFileAttributes basicAttr = Files.readAttributes(file1, BasicFileAttributes.class);
                            FileTime creationTime = basicAttr.creationTime();
                            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd.MMM yyyy. HH:mm:ss");
                            System.out.println("4. Creation date/time is " + creationTime);

                            Instant instant = Instant.ofEpochMilli(file.lastModified());
                            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MMM yyyy. HH:mm:ss");
                            System.out.println("5. Last modified is " + dateTime.format(dtf));
                        } else {
                            System.out.println("Sorry, your  directory doesn't exists!");
                        }
                        break;
                    }

                case CREATE:
                    System.out.print("Enter path or directory: ");
                    try (BufferedReader brCreate = new BufferedReader(new InputStreamReader(System.in));) {
                        String sCreate = brCreate.readLine();
                        File testDirectory = new File(sCreate);
                        try {
                            if (!testDirectory.exists()) {
                                testDirectory.mkdir();
                                System.out.println("Created a directory called " + testDirectory.getName());
                            } else {
                                System.out.println("Sorry, directory called " + testDirectory.getName() + "already exists");
                            }
                        } catch (Exception e) {
                            System.out.println("Couldn't create a directory called " + testDirectory.getName());
                        }
                        break;
                    }

                case RENAME:
                    System.out.print("Enter path or directory: ");
                    try (BufferedReader brRename1 = new BufferedReader(new InputStreamReader(System.in));) {
                        String sRename1 = brRename1.readLine();
                        File oldFile = new File(sRename1);
                        System.out.print("Enter a new directory name: ");
                        try (BufferedReader brRename2 = new BufferedReader(new InputStreamReader(System.in));) {
                            String sRename2 = brRename2.readLine();
                            File newFile = new File(sRename2);

                            if (!oldFile.exists()) {
                                System.out.println("Directory doesn't exists!");
                            }

                            if (newFile.exists()) {
                                System.out.println("Directory with name already exists!");
                            }

                            if (oldFile.renameTo(newFile)) {
                                System.out.println("Rename succesful!!");
                            } else {
                                System.out.println("Sorry, rename failed");
                            }
                            break;
                        }
                    }
                case COPY:
                    System.out.println("Enter file to copy: ");
                    try (BufferedReader brCopy1 = new BufferedReader(new InputStreamReader(System.in));) {
                        String sCopy1 = brCopy1.readLine();
                        File aFile = new File(sCopy1);
                        System.out.print("Enter file in which you want to copy the previous file: ");

                        try (BufferedReader brCopy2 = new BufferedReader(new InputStreamReader(System.in));) {
                            String sCopy2 = brCopy2.readLine();
                            File bFile = new File(sCopy2);

                            try (FileInputStream inStream = new FileInputStream(aFile);
                                    FileOutputStream outStream = new FileOutputStream(bFile);) {

                                byte[] buffer = new byte[1024];

                                int length;
                                while ((length = inStream.read(buffer)) > 0) {
                                    outStream.write(buffer, 0, length);
                                }
                                System.out.println("File is copied succesfuly!");
                            }
                            break;
                        }
                    }
                case MOVE:
                    System.out.println("Enter directory which you want to move: ");
                    try (BufferedReader brMove1 = new BufferedReader(new InputStreamReader(System.in));) {
                        String sMove1 = brMove1.readLine();
                        Path source = Paths.get(sMove1);
                        System.out.print("Enter path in which you want to move the previous directory: ");
                        try (BufferedReader brMove2 = new BufferedReader(new InputStreamReader(System.in));) {
                            String sMove2 = brMove2.readLine();
                            Path destination = Paths.get(sMove2);
                            try {

                               if (!Files.exists(source)) {
                                    System.out.println("Directory doesn't exists!");
                                }

                                if (!Files.exists(destination.getParent())) {
                                    System.out.println("Location doesn't exists!");
                                }

                                if (Files.exists(destination)) {
                                    System.out.println("Directory already exists on the location!");
                                }

                                Files.copy(source, destination);
                                System.out.println("Directory " + source.getFileName() + " is copied!");
                            } catch (IOException ex) {
                                System.out.println("ex");
                            }
                            break;
                        }
                    }
                case DELETE:
                    System.out.println("Enter file to delete: ");
                    try (BufferedReader brDelete = new BufferedReader(new InputStreamReader(System.in));) {
                        String sDelete = brDelete.readLine();
                        File file = new File(sDelete);

                        if (file.exists()) {
                            file.delete();
                            System.out.println("Directory has deleted!");
                        } else {
                            System.out.println("Sorry, cannot delete " + file.getName() + " because " + file.getName() + " directory doesn't exists!");
                        }
                        break;
                    } catch (IOException exc) {
                        System.out.println(exc);
                    }

                default:
                    System.out.println("Sorry, wrong command! Please,try again.");
                    break;
            }
        }
    }
}

    
    

