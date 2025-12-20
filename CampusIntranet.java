import java.util.*;

// Directory node (tree)
class TreeDir {
    String dname;
    List<String> files;
    List<TreeDir> subdirs;

    String ownerGroup;           // faculty / students / both
    int[][] perm;                // [0]=owner, [1]=others  [r=0,w=1,x=2]

    TreeDir(String name, String ownerGroup, String permString) {
        this.dname = name;
        this.ownerGroup = ownerGroup;
        this.files = new ArrayList<>();
        this.subdirs = new ArrayList<>();
        this.perm = new int[2][3];

        // Convert permission string (rwxr-x)
        for (int i = 0; i < 6; i++) {
            int g = (i < 3) ? 0 : 1;
            int p = i % 3;
            perm[g][p] = (permString.charAt(i) != '-') ? 1 : 0;
        }
    }

    // Check permission
    boolean checkPermission(String userGroup, int action) {
        int group = userGroup.equals(ownerGroup) ? 0 : 1;
        return perm[group][action] == 1;
    }

    // Print permissions
    void printPerm() {
        StringBuilder sb = new StringBuilder("[");
        for (int g = 0; g < 2; g++) {
            for (int p = 0; p < 3; p++) {
                sb.append(perm[g][p] == 1 ? "rwx".charAt(p) : '-');
            }
        }
        sb.append("]");
        System.out.print(" " + sb);
    }

    // Display tree recursively
    void display(int level) {
        System.out.print("   ".repeat(level));
        System.out.print("📁 " + dname);
        printPerm();
        System.out.println(" (Owner: " + ownerGroup + ")");

        for (String f : files) {
            System.out.print("   ".repeat(level + 1));
            System.out.println("📄 " + f);
        }

        for (TreeDir d : subdirs) {
            d.display(level + 1);
        }
    }
}

public class DirectorySystem {

    // Navigate using path
    static TreeDir navigate(TreeDir root, String path) {
        if (path.equals("/")) return root;

        String[] parts = path.split("/");
        TreeDir curr = root;

        for (String part : parts) {
            if (part.isEmpty()) continue;
            boolean found = false;

            for (TreeDir d : curr.subdirs) {
                if (d.dname.equals(part)) {
                    curr = d;
                    found = true;
                    break;
                }
            }
            if (!found) return null;
        }
        return curr;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Login as (faculty / student): ");
        String currentUser = sc.next();

        TreeDir root = new TreeDir("root", "both", "rwxrwx");

        while (true) {
            System.out.println("\n========= DIRECTORY SYSTEM =========");
            System.out.println("1. Create File");
            System.out.println("2. Create Subdirectory");
            System.out.println("3. Display Tree");
            System.out.println("4. Exit");
            System.out.print("Choice: ");

            int ch = sc.nextInt();

            if (ch == 1) {
                System.out.print("Enter directory path: ");
                String path = sc.next();

                TreeDir d = navigate(root, path);
                if (d == null) {
                    System.out.println("Directory not found!");
                    continue;
                }
                if (!d.checkPermission(currentUser, 1)) {
                    System.out.println("❌ Permission Denied (write)");
                    continue;
                }

                System.out.print("Enter filename: ");
                d.files.add(sc.next());
                System.out.println("✔ File added.");
            }

            else if (ch == 2) {
                System.out.print("Enter parent directory path: ");
                String path = sc.next();

                TreeDir d = navigate(root, path);
                if (d == null) {
                    System.out.println("Invalid path!");
                    continue;
                }
                if (!d.checkPermission(currentUser, 1)) {
                    System.out.println("❌ Permission Denied (write)");
                    continue;
                }

                System.out.print("Enter new directory name: ");
                String name = sc.next();

                System.out.print("Owner group (faculty/students/both): ");
                String group = sc.next();

                System.out.print("Enter rwx permissions (e.g., rwxr-x): ");
                String perm = sc.next();

                d.subdirs.add(new TreeDir(name, group, perm));
                System.out.println("✔ Subdirectory created.");
            }

            else if (ch == 3) {
                root.display(0);
            }

            else break;
        }

        sc.close();
    }
}
