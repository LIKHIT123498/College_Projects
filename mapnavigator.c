#include <stdio.h>
#include <limits.h>
#include <stdbool.h>
#define V 6
#define INF 9999
char *buildings[V] = {
"Main Gate",
"Admin Block",
"Library",
"Academic Block",
"Canteen",
"Girls Hostel"
};
int minDistance(int dist[], bool visited[]) {
int min = INF, min_index;
for (int v = 0; v < V; v++)
if (!visited[v] && dist[v] <= min)
min = dist[v], min_index = v;
return min_index;
}
void printPath(int parent[], int j) {
if (parent[j] == -1) return;
printPath(parent, parent[j]);
printf(" -> %s", buildings[j]);
}
void dijkstra(int graph[V][V], int src, int dest) {
int dist[V], parent[V];
bool visited[V];
for (int i = 0; i < V; i++) {
dist[i] = INF;
visited[i] = false;
parent[i] = -1;
}
dist[src] = 0;
for (int count = 0; count < V - 1; count++) {
int u = minDistance(dist, visited);
visited[u] = true;
for (int v = 0; v < V; v++)
if (!visited[v] && graph[u][v] && dist[u] != INF && dist[u] +
graph[u][v] < dist[v]) {
dist[v] = dist[u] + graph[u][v];
parent[v] = u;
}
}
if (dist[dest] == INF) {
printf("\n[!] No path from %s to %s\n", buildings[src], buildings[dest]);
} else {
printf("\n \n");
printf("Shortest Path: %s", buildings[src]);
printPath(parent, dest);
printf("\nTotal Distance: %d meters\n", dist[dest]);
printf(" \n");
}
}
int main() {
int graph[V][V] = {
{ 0, 100, 0, 200, 0, 0 },
{100, 0, 50, 0, 0, 0 },
{ 0, 50, 0, 0, 100, 0 },
{200, 0, 0, 0, 80, 300 },
{ 0, 0, 100, 80, 0, 150 },
{ 0, 0, 0, 300, 150, 0 }
};
printf("\n=== CAMPUS MAP NAVIGATOR ===\n");
printf("0: Main Gate | 1: Admin | 2: Library\n");
printf("3: Academic | 4: Canteen | 5: Girls Hostel\n");
int start, end;
printf("\nEnter Start Node (0-5): ");
scanf("%d", &start);
printf("Enter End Node (0-5): ");
scanf("%d", &end);
if (start >= 0 && start < V && end >= 0 && end < V) {
dijkstra(graph, start, end);
} else {
printf("Invalid input! Please enter numbers between 0 and 5.\n");
}
return 0;
}
