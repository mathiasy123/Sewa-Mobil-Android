package com.path_studio.arphatapp.InboxRecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class InboxData {
    public static String[][] data = new String[][]{
            {"patriciafiona3@gmail.com", "Percobaan recycler view 01", "https://upload.wikimedia.org/wikipedia/commons/3/3a/Ki_Hadjar_Dewantara_Mimbar_Umum_18_October_1949_p2.jpg"},
            {"fiona_angel@gmail.com", "Percobaan recycler view 02", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/VP_Hatta.jpg/330px-VP_Hatta.jpg"},
            {"andre80_siahaan@yahoo.co.id", "Percobaan recycler view 03", "https://upload.wikimedia.org/wikipedia/commons/b/be/Col_Gatot_Subroto%2C_Kenang-Kenangan_Pada_Panglima_Besar_Letnan_Djenderal_Soedirman%2C_p27.jpg"},
            {"melly_cong@gmail.com", "Percobaan recycler view 04", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/330px-Presiden_Sukarno.jpg"},
            {"patriciafiona3@gmail.com", "Percobaan recycler view 01", "https://upload.wikimedia.org/wikipedia/commons/3/3a/Ki_Hadjar_Dewantara_Mimbar_Umum_18_October_1949_p2.jpg"},
            {"fiona_angel@gmail.com", "Percobaan recycler view 02", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/VP_Hatta.jpg/330px-VP_Hatta.jpg"},
            {"andre80_siahaan@yahoo.co.id", "Percobaan recycler view 03", "https://upload.wikimedia.org/wikipedia/commons/b/be/Col_Gatot_Subroto%2C_Kenang-Kenangan_Pada_Panglima_Besar_Letnan_Djenderal_Soedirman%2C_p27.jpg"},
            {"melly_cong@gmail.com", "Percobaan recycler view 04", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/330px-Presiden_Sukarno.jpg"},
            {"patriciafiona3@gmail.com", "Percobaan recycler view 01", "https://upload.wikimedia.org/wikipedia/commons/3/3a/Ki_Hadjar_Dewantara_Mimbar_Umum_18_October_1949_p2.jpg"},
            {"fiona_angel@gmail.com", "Percobaan recycler view 02", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/VP_Hatta.jpg/330px-VP_Hatta.jpg"},
            {"andre80_siahaan@yahoo.co.id", "Percobaan recycler view 03", "https://upload.wikimedia.org/wikipedia/commons/b/be/Col_Gatot_Subroto%2C_Kenang-Kenangan_Pada_Panglima_Besar_Letnan_Djenderal_Soedirman%2C_p27.jpg"},
            {"melly_cong@gmail.com", "Percobaan recycler view 04", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/330px-Presiden_Sukarno.jpg"},
            {"patriciafiona3@gmail.com", "Percobaan recycler view 01", "https://upload.wikimedia.org/wikipedia/commons/3/3a/Ki_Hadjar_Dewantara_Mimbar_Umum_18_October_1949_p2.jpg"},
            {"fiona_angel@gmail.com", "Percobaan recycler view 02", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/VP_Hatta.jpg/330px-VP_Hatta.jpg"},
            {"andre80_siahaan@yahoo.co.id", "Percobaan recycler view 03", "https://upload.wikimedia.org/wikipedia/commons/b/be/Col_Gatot_Subroto%2C_Kenang-Kenangan_Pada_Panglima_Besar_Letnan_Djenderal_Soedirman%2C_p27.jpg"},
            {"melly_cong@gmail.com", "Percobaan recycler view 04", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Presiden_Sukarno.jpg/330px-Presiden_Sukarno.jpg"}
    };
    public static Collection<? extends Inbox> getListData(){
        ArrayList<Inbox> list = new ArrayList<>();
        for (String[] aData : data) {
            Inbox inbox = new Inbox();
            inbox.setEmail(aData[0]);
            inbox.setFrom(aData[1]);
            inbox.setPhoto(aData[2]);
            list.add(inbox);
        }
        return list;
    }
}
