
import Server.Game_Server_Ex2;
import  api.*;
import gameClient.*;

public class Main {

    public static void main(String[] args) {
        game_service game= Game_Server_Ex2.getServer(0);
        System.out.println(game.getPokemons());
        directed_weighted_graph g=game.getJava_Graph_Not_to_be_used();

        String str="";

        for (node_data v:g.getV()){
            str += v.getKey() + ":";
            for (edge_data e:g.getE(v.getKey())){
//                System.out.print("---> " + e.getDest()  + ", " );
                str+="---> " + e.getDest()  + ", " ;
            }
            str += "\n";

        }
        System.out.println(str);
    }
//
//            0:---> 1, ---> 10,
//            1:---> 0, ---> 2,
//            2:---> 1, ---> 3,
//            3:---> 2, ---> 4,
//            4:---> 3, ---> 5,
//            5:---> 4, ---> 6,
//            6:---> 5, ---> 7,
//            7:---> 6, ---> 8,
//            8:---> 7, ---> 9,
//            9:---> 8, ---> 10,
//            10:---> 0, ---> 9,

}
