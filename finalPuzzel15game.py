import tkinter as tk

import random
import copy
import heapq

goal_state=[
    [1,2,3,4],
    [5,6,7,8],
    [9,10,11,12],
    [13,14,15,0]

    # 0  هنا معناها انها مربع فاضي او مكان فاضي
]

actions =[(-1,0),(1,0),(0,-1),(0,1)]
#ال variable دا عشان التحركات اللي نقدر نعملها في اي حالة 



##تتعبر عدد الحركات اللي المفروض المربع اللي احنا واقفين عنده عشان يوصل للمكان الهدف النهائي  
def manhattan_distance (s):
    distance=0
    # 4    عشان دي بازل 15 يعني 16 مكان 
    # x for rows & y for col.
    for x in range(4):
        for y in range (4):
            value=s[x][y]    # دا المربع يعني
            if value !=0:         #يعني مش المربع الفاضي
                   location_x=(value-1)//4    #for know the row goal
                   location_y=(value-1)%4      #for know the col.gosl
                   distance += abs(x - location_x) + abs(y - location_y)    # دا قانون المسافة "كنا ااخدينه ف تالته ثانوي هندسة فراغية" 
                   #عشان اعرف المسافة بالاحداثيات اللي هي هتكون صفوف و اعمدة بين المكان الحالي و الهدف النهائي بقا 
    return distance              

#Find empty box
def findZero (s):
     for x in range(4):
          for y in range(4):
               if s[x][y]==0:
                    return x,y
               #عشان نعرف مكان ال 0 و نعرف كمان الارقام اللي حواليه 
               #و عشان نعرف ال actions اللي ممكن ناخدها في ال state دي 

#بجيب الحركات اللي اقدر اعملها و الارقام المجاورة 
def get_neighbors (s):
     x,y =findZero(s)
     neighborsNumbers=[]  # عشان احط فيها الارقام اللي حوالين ال مرعب الفارغ
     for dx,dy in actions:      #الحركات اللي اعملها 
          newX=x+dx  #new location of the numbers in rows
          newY=y+dy #new location of the numbers in cols.

          if 0 <= newX < 4 and 0 <= newY < 4:   #البازل 4*4 ف الحدود من 0 ل 3          #بشوف المكان عشان لازم يكون في حدود البازل 
               new_state = copy.deepcopy(s)  #نأخذ نسخة من الحالة  باستخدام deepcopy لكي نتمكن من تعديلها بدون التأثير على الحالة الأصلية
               new_state[x][y], new_state[newX][newY] = new_state[newX][newY], new_state[x][y]   #بعمل الحركة ف ببدل المربع الجديد ب المربع الفارغ 
               neighborsNumbers.append(new_state)  #بجمع الحركات اللي اقدر اعملها في القائمة 

     return neighborsNumbers                 

#بحول المصفوفة للحالة الي نص عشان اقدر اخزنها في dictionary و استخدم keys 
def STR (state):
     return str(state)


# main algoritm for solving the puzzel with A* search algorithm
def A_starALGORITHM(start):
    open_list = []   #priority queue 
    heapq.heappush(open_list, (manhattan_distance(start), 0, start))  #بحط مكان اللي هتتخزن فيه و بحط الدالة بتاعت المسافة لاول رقم و ال cost هيكون صفر 
                #   priority queue      h(n)             g(n)    node
    parent = {STR(start): None}   #لازم بس الاول احول النود ل string 
    #يخزن عدد الحركات التي قمنا بها للوصول إلى كل حالة.
    g_score = {STR(start): 0} # Cost from start to each node
  
    visited = set()  #قمنا بزيارتها بالفعل

    while open_list:
        f, g, current = heapq.heappop(open_list)     #ناخد اقل f-cost 
        path = []
        if current == goal_state:
           
            while current:
                path.append(current)
                current = parent[STR(current)]
            return path[::-1]

        visited.add(STR(current))

        for neighbor in get_neighbors(current):
            neighbor_key = STR(neighbor)
            if neighbor_key in visited:
                continue
# g-> التكلفة اللي وصلتنا للحالة الحالية 
            tentative_g = g + 1   # تكلفة الوصول للرقم المجاور او المربع المجاور و هي دايما ثابته 
            if neighbor_key not in g_score or tentative_g < g_score[neighbor_key]:      #لو التكلفة الجديدة اقل من التكلفة السابقة بنحدث ال f_score و ال g_score و نضيفها لل priority queue 
                g_score[neighbor_key] = tentative_g
                f_score = tentative_g + manhattan_distance(neighbor)   #f(n)=g(n)+h(n)
                heapq.heappush(open_list, (f_score, tentative_g, neighbor))    #هنضيف ال مربع الجديد او الحالة الجديدة بناء على التكلفة الااقلل
                parent[neighbor_key] = current   #بنسجل ان ال neighbor جي من ال current 

    return None



#بتعمل حركات عشوائية تخلي اللعبة صعبة 
def initial_state_newRandom (moves=100):
     state=copy.deepcopy(goal_state)

     Rmove=actions

     for i in range(moves):
          dx,dy=random.choice(Rmove)
          x,y=findZero(state)

          newx=x+dx
          newy=y+dy

          if 0 <= newx < 4 and 0 <= newy < 4:
               state[x][y], state[newx][newy] = state[newx][newy], state[x][y]
     return state           

class PuzzleGUI:
    def __init__(self, root):

        self.root = root
        self.root.title("Puzzel 15 Game")
        self.state = initial_state_newRandom()
        self.buttons = [[None]*4 for _ in range(4)]
        self.create_widgets()
        self.count = 0

    def create_widgets(self):
        for i in range(4):
            for j in range(4):
                btn = tk.Button(self.root, text="", width=4, height=2, font=("Arial", 24))
                btn.grid(row=i, column=j, padx=5, pady=5)
                self.buttons[i][j] = btn
                self.movesButton = tk.Label(self.root, text="steps:0", font=("Arial", 14), bg="#0B0B61", fg="white")
                self.movesButton.grid(row=5, column=0, columnspan=4, pady=10)

                

        tk.Button(self.root, bg="#B43104",text="Shuffle", font=("Arial", 14), command=self.shuffle).grid(row=4, column=0, columnspan=2, pady=10)
        tk.Button(self.root,bg="#31B404", text="Solve", font=("Arial", 14), command=self.solve).grid(row=4, column=2, columnspan=2, pady=10)
        self.update_ui()

    def update_ui(self):
        for i in range(4):
            for j in range(4):
                val = self.state[i][j]
                self.root.configure(bg="#0B0B61")  # لون الخلفية الكبيرة اللي تحت كل مربع ل كل رقم 
                self.buttons[i][j].config(text=str(val) if val != 0 else "", bg="#0B0B61" if val == 0 else "#ecf0f1")
                #                                       not the empty box      empty box                neighbor numbers

    def shuffle(self):
        self.state = initial_state_newRandom()
        self.count = 0
        self.movesButton.config(text="steps:0")    # لما بعمل shuffel ف برجع عدد ال steps ل صفر 

        self.update_ui()

    def solve(self):
        solution =A_starALGORITHM(self.state)
        if solution:
            self.animate(solution)
        else:
            print("No solution found.")

    def animate(self, solution):
        

        
        def step(i=0):
            if i < len(solution):        #عدد الخطوات اللي لازم نعرضها.                #معناها: لو لسه في خطوات، كمل عرض؛ لو خلصت، وقف.
                self.state = solution[i]
                self.update_ui()
                self.root.after(300, step, i+1)
                self.count += 1
                self.movesButton.config(text=f"steps: {self.count}")
               
                
              
        step()
        

if __name__ == "__main__":
    root = tk.Tk()
    app = PuzzleGUI(root)
    
    
    root.mainloop()
















