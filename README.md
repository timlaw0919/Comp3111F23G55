# Comp3111F23G55

![image](https://github.com/timlaw0919/Comp3111F23G55/assets/144464604/e9ac81d5-6199-448c-ad82-53700afdddf9)


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Code Explanation
[Function A] - Create Maze
### Basic Logic
1. Set up the 30*30 maze with BLOCKS
2. Random Select a Cell on the edge as EntryPoint
3. Start the create the PATH
4. Set the EntryPoint as CurrentCell
5. Expand the PATH by randomly choosing one of the Neighbours of the CurrentCell
6. Update the RandomNeighbours as the CurrentCell
7. <Repeat 5-6> until no more Cell has vaild Neighbour

### QnA
? How to set the ExitPoint
- Criteria: the first Neighbour of the CurrentCell on the opposite edge of EntryPoint
  
? What does it mean by "vaild Neighbour"
- Criteria_1: not on the edge (except for the EntryPoint and ExitPoint)
- Criteria_2: the max number of PATH Neighbour of this "vaild Neigbour" is 3
- Criteria_3: unvisited
  
? Why there are more then 2 possible paths
- Stopping criteria is "no more Cell has vaild Neighbour" but not "ExitPoint is reached" -> keep on removing BLOCKS after the first possible path is created so more PATH possibilty
- Based on the 2nd criteria of "vaild Neighbour" -> fewer deadholes are created

### Remarks
1. the edges are all BLOCKs (except for the EntryPoint and ExitPoint)
2. Color: Yellow (EntryPoint); Color: Light Green (ExitPoint); Color: White (PATH); Colour: Grey (BLOCK)

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

[Function B] - Shortest Path
### Basic Logic
1. Get the location of Tom and Jerry
2. Calculate the total cost of Tom (forward cost by Manhattan Distance and backward cost by each movement cost is 1)
3. Add Tom's Node into the List - frontier
4. Select the lowest cost node from the frontier to be the current node
5. Remove the current node from the List - frontier and add to the List - expanded
6. Expand all neighbours of the current node
7. Mark down all neighbours' parent (i.e. current node)
8. Calculate the total cost of each neighbour (forward by Manhattan Distance and backward cost (Each movement cost is 1))
9. Add the valid neighbours of the current node to the List - frontier
10. Repeat steps 4 to 8 until reaching the Jerry's location
11. Add the coordinates to the List - path

### QnA
? How to know which neighbours are valid
- The first criterion is the coordinate of row and col is within [0, 30)
- The second criterion is it cannot be expanded or added to the frontier already
  - -> Since the neighbours are already inside the expanded list or frontier list, there must be some paths which reach this neighbour with lower total cost.
  - -> Then the later path which expands the same neighbours can be thrown away (it must not be the shortest path)

### How to use the code
1. Create an AStarAlgorithm object given the original Tom and Jerry Location
   - Code: AStarAlgorithm object = new AStarAlgorithm(tomLocation:int[], jerryLocation:int[]);
3. Get the next coordinate that Tom should move
   - Code: object.tomNextMovement();
   - I.e. It will return a int[] with index 0 is x coordinate and index 1 is y coordinate
5. Update Tom's Location
   - Code: object.changeTomLocation(tomLocation:int[]);
   - I.e. Same as previous, index 0 is x coordinate and index 1 is y coordinate
6. Update Jerry's Location
   - Code: object.changeJerryLocation(jerryLocation:int[]);
   - I.e. Same as previous, index 0 is x coordinate and index 1 is y coordinate

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

