package com.path_studio.arphatapp.HistoryRecyclerView;

import com.path_studio.arphatapp.InboxRecyclerView.Inbox;

import java.util.ArrayList;
import java.util.Collection;

public class HistoryData {
    public static String[][] data = new String[][]{
            {
                    "Booking - Waiting",
                    "12 Apr 2018",
                    "Bandung Raya, Kabupaten Bandung, Provinsi Jawa Barat, Indonesia",
                    "Puncak, Kabupaten Bogor, Provinsi Jawa Barat, Indonesia",
                    "Rp.750.000"
            },
            {
                    "Booking - Finished",
                    "28 Sep 2017",
                    "Karawang Selatan, Kabupaten Karawang, Jawa Barat, Indonesia",
                    "Jakarta Utara, DKI Jakarta, Indonesia",
                    "Rp.680.000"
            },
            {
                    "Booking - Waiting",
                    "12 Apr 2018",
                    "Bandung Raya, Kabupaten Bandung, Provinsi Jawa Barat, Indonesia",
                    "Puncak, Kabupaten Bogor, Provinsi Jawa Barat, Indonesia",
                    "Rp.750.000"
            },
            {
                    "Booking - Finished",
                    "28 Sep 2017",
                    "Karawang Selatan, Kabupaten Karawang, Jawa Barat, Indonesia",
                    "Jakarta Utara, DKI Jakarta, Indonesia",
                    "Rp.680.000"
            },
            {
                    "Booking - Waiting",
                    "12 Apr 2018",
                    "Bandung Raya, Kabupaten Bandung, Provinsi Jawa Barat, Indonesia",
                    "Puncak, Kabupaten Bogor, Provinsi Jawa Barat, Indonesia",
                    "Rp.750.000"
            },
            {
                    "Booking - Finished",
                    "28 Sep 2017",
                    "Karawang Selatan, Kabupaten Karawang, Jawa Barat, Indonesia",
                    "Jakarta Utara, DKI Jakarta, Indonesia",
                    "Rp.680.000"
            },
            {
                    "Booking - Waiting",
                    "12 Apr 2018",
                    "Bandung Raya, Kabupaten Bandung, Provinsi Jawa Barat, Indonesia",
                    "Puncak, Kabupaten Bogor, Provinsi Jawa Barat, Indonesia",
                    "Rp.750.000"
            },
            {
                    "Booking - Finished",
                    "28 Sep 2017",
                    "Karawang Selatan, Kabupaten Karawang, Jawa Barat, Indonesia",
                    "Jakarta Utara, DKI Jakarta, Indonesia",
                    "Rp.680.000"
            }
    };
    public static Collection<? extends History> getListData(){
        ArrayList<History> list = new ArrayList<>();
        for (String[] aData : data) {
            History history = new History();
            history.setStatus(aData[0]);
            history.setTanggal(aData[1]);
            history.setLokasi_start(aData[2]);
            history.setLokasi_end(aData[3]);
            history.setHarga(aData[4]);
            list.add(history);
        }
        return list;
    }
}
