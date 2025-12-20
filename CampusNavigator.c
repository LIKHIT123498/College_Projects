#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Directory node (tree)
struct treeDir {
    char dname[20];

    char fname[10][20];
    int fcount;

    struct treeDir* subdir[10];
    int subcount;

    char ownerGroup[20];   // faculty / students / both
    int perm[2][3];        // [0] = owner perm, [1] = others; [read=0,write=1,exec=2]
};

// ----------------------------
// Linux Permission Check
// ----------------------------
int checkPermission(struct treeDir* d, char userGroup[], int action) {
    int group = strcmp(userGroup, d->ownerGroup) == 0 ? 0 : 1;
    return d->perm[group][action];
}

// ----------------------------
// Create directory
// ----------------------------
struct treeDir* createDir(char name[], char ownerGroup[], char permString[]) {
    struct treeDir* newdir = (struct treeDir*)malloc(sizeof(struct treeDir));

    strcpy(newdir->dname, name);
    strcpy(newdir->ownerGroup, ownerGroup);

    newdir->fcount = 0;
    newdir->subcount = 0;

    // Convert permission string rwxr-x---
    // owner = chars 0-2, others = chars 3-5
    for (int i = 0; i < 6; i++) {
        int g = (i < 3) ? 0 : 1;
        int p = i % 3;

        newdir->perm[g][p] = (permString[i] != '-') ? 1 : 0;
    }

    return newdir;
}

// ----------------------------
// Display permissions like rwxr-xr--
// ----------------------------
void printPerm(struct treeDir* d) {
    char out[10];
    int idx = 0;

    for (int g = 0; g < 2; g++)
        for (int p = 0; p < 3; p++)
            out[idx++] = d->perm[g][p] ? ("rwx"[p]) : '-';

    out[6] = '\0';
    printf(" [%s]", out);
}

// ----------------------------
// Display directory tree
// ----------------------------
void display(struct treeDir* d, int level) {
    for (int i = 0; i < level; i++)
        printf("   ");

    printf("📁 %s", d->dname);
    printPerm(d);
    printf("  (Owner: %s)\n", d->ownerGroup);

    for (int i = 0; i < d->fcount; i++) {
        for (int j = 0; j < level + 1; j++)
            printf("   ");
        printf("📄 %s\n", d->fname[i]);
    }

    for (int i = 0; i < d->subcount; i++)
        display(d->subdir[i], level + 1);
}

// ----------------------------
// Search directory by path
// Example: CSE/faculty/courses
// ----------------------------
struct treeDir* navigate(struct treeDir* root, char path[]) {
    if (strcmp(path, "/") == 0) return root;

    char temp[100];
    strcpy(temp, path);

    struct treeDir* curr = root;

    char* token = strtok(temp, "/");
    while (token != NULL) {
        int found = 0;

        for (int i = 0; i < curr->subcount; i++) {
            if (strcmp(curr->subdir[i]->dname, token) == 0) {
                curr = curr->subdir[i];
                found = 1;
                break;
            }
        }

        if (!found) return NULL;
        token = strtok(NULL, "/");
    }

    return curr;
}

// ----------------------------
// Main Program
// ----------------------------
int main() {
    char currentUser[20];
    printf("Login as (faculty / student): ");
    scanf("%s", currentUser);

    struct treeDir* root = createDir("root", "both", "rwxrwx");

    int ch;
    char path[100], name[20], perm[10], group[20];

    while (1) {
        printf("\n========= DIRECTORY SYSTEM =========\n");
        printf("1. Create File\n");
        printf("2. Create Subdirectory\n");
        printf("3. Display Tree\n");
        printf("4. Exit\n");
        printf("Choice: ");
        scanf("%d", &ch);

        if (ch == 1) {
            printf("Enter directory path: ");
            scanf("%s", path);
            struct treeDir* d = navigate(root, path);

            if (!d) { printf("Directory not found!\n"); continue; }
            if (!checkPermission(d, currentUser, 1)) {
                printf("❌ Permission Denied (write)\n");
                continue;
            }

            printf("Enter filename: ");
            scanf("%s", d->fname[d->fcount]);
            d->fcount++;

            printf("✔ File added.\n");
        }

        else if (ch == 2) {
            printf("Enter parent directory path: ");
            scanf("%s", path);
            struct treeDir* d = navigate(root, path);

            if (!d) { printf("Invalid path!\n"); continue; }
            if (!checkPermission(d, currentUser, 1)) {
                printf("❌ Permission Denied (write)\n");
                continue;
            }

            printf("Enter new directory name: ");
            scanf("%s", name);

            printf("Owner group (faculty/students/both): ");
            scanf("%s", group);

            printf("Enter rwx permissions (e.g., rwxr-x): ");
            scanf("%s", perm);

            d->subdir[d->subcount] = createDir(name, group, perm);
            d->subcount++;

            printf("✔ Subdirectory created.\n");
        }

        else if (ch == 3) {
            display(root, 0);
        }

        else break;
    }

    return 0;
} 
