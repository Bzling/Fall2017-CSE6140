#!E:/Eclipse-Workspace/CSEQ1/src/TEST
import networkx as nx
import time
from operator import itemgetter
import sys
from oauthlib.uri_validate import path
#from networkx.classes import graph

class DisjointSet(dict):
        def add(self,item):
            self[item] = item;
        def find(self, item):
            parent = self[item];
            while self[parent]!= parent:
                parent = self[parent]
            self[item] = parent
            return parent
        def union(self, item1, item2):
            self[item2] = self[item1] 
                               
#class RunExperiments:
def read_graph(filename):
    G = nx.MultiGraph()
    with open(filename,'r') as f:
        myfile = f.readline()
        for line in f:
            edge_data = list(map(lambda x: int(x), line.split()))
            assert(len(edge_data)==3)
            G.add_edge(edge_data[0], edge_data[1], weight = edge_data[2])
        return G  
                 
    #def getMST(self,graphfile):
        #forest = DisjointSet()
        #MST = nx.MultiGraph()
        #for n in graphfile.nodes():
            #forest.add(n)
        #size = len(graphfile.nodes()) - 1
        #edges = list(graphfile.edges(data = True))
        #for e in sorted(edges, key = itemgetter(2)):
            #n1, n2, w = e
            #t1 = forest.find(n1)
            #t2 = forest.find(n2)
            #if t1 != t2:
                #MST.add_edge(n1, n2, weight = w['weight'])
                #size = size - 1
                #if size == 0:
                    #break
                #forest.union(t1, t2)
        #return MST
        
def computeMST(graphfile):
    forest = DisjointSet()
    MST = nx.MultiGraph()
    for n in graphfile.nodes():
        forest.add(n)
    size = len(graphfile.nodes()) - 1
        #MST = self.getMST(graphfile)
    edges = list(graphfile.edges(data = True))
    edgeList = []
    for e in sorted(edges, key = itemgetter(2)):
        n1, n2, w = e
        t1 = forest.find(n1)
        t2 = forest.find(n2)
        if t1 != t2:
            MST.add_edge(n1, n2, weight = w['weight'])
            edgeList.append(w)
            size = size - 1
            if size == 0:
                break
            forest.union(t1, t2)                
    MSTweight = 0
    for item in edgeList:
        MSTweight = MSTweight + item['weight']
    return MST, MSTweight
    
def recomputeMST(addnode1, addnode2, addweight, graphfile, oldMSTweight):
        #MST = self.getMST(graphfile)
        #MSTPath = []
    newMST = nx.MultiGraph()
    for path in nx.all_simple_paths(graphfile, addnode1, addnode2):
        MSTPath = path
    MSTPathWeight = []
    for i in xrange(len(MSTPath)-1):
        diction = graphfile.get_edge_data(MSTPath[i], MSTPath[i+1])
        MSTPathWeight.append(diction[0]['weight'])
    MaxWeight = max(MSTPathWeight)
    for i in xrange(len(MSTPath)-1):
        diction = graphfile.get_edge_data(MSTPath[i], MSTPath[i+1])
        x = int(diction[0]['weight'])
        if x == MaxWeight:
            a= MSTPath[i]
            b= MSTPath[i+1]
    if MaxWeight > addweight:
        add_weight = addweight - MaxWeight
        graphfile.remove_edge(a, b)
        newMST = graphfile
        newMST.add_edge(addnode1, addnode2, weight = addweight)
    else:
        add_weight = 0
        newMST = graphfile
            
    newWeight =  oldMSTweight + add_weight    
    return newMST, newWeight
    
def main():        
    num_args = len(sys.argv)
    if num_args < 4:
        print"error:not enough input arguments"
        exit(1)
            
    graph_file = sys.argv[1]
    change_file = sys.argv[2]
    output_file = sys.argv[3]
        
    G = read_graph(graph_file)
    start_MST = time.time()
    MST, MSTweight = computeMST(G)
    total_time = (time.time() - start_MST) * 1000 #to convert to milliseconds
    print total_time    
        #Write initial MST weight and time to file
    output = open(output_file, 'w')
    output.write("MSTweight:"+ str(MSTweight) + " " + str(total_time) + '\n' + "Change data:" + '\n')
        
        #Changes file
    with open(change_file, 'r') as changes:
        num_changes = changes.readline()
            
        for line in changes:
                #parse edge and weight
            edge_data = list(map(lambda x: int(x), line.split()))
            assert(len(edge_data) == 3)
                
            u,v,weight = edge_data[0], edge_data[1], edge_data[2]
                
                #call recomputeMST function
            start_recompute = time.time()
            MST, MSTweight = recomputeMST(u, v, weight, MST, MSTweight)
            total_recompute = (time.time() - start_recompute) * 1000 # to convert to milliseconds
            print total_recompute    
                #write new weight and time to output file
            output.write(str(MSTweight) + " " + str(total_recompute) + '\n')
                
if __name__ == '__main__':
    # run the experiments
    #runexp = RunExperiments()
    #runexp.main()
    main()