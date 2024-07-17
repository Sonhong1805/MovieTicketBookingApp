package com.example.mybtl;

import android.content.Context;

import java.util.ArrayList;

public class MovieDAO {
    public static ArrayList<Movie> getAll(Context context) {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1,"Vùng Đất Câm Lặng: Ngày Một",R.drawable.a_quiet_place,"Sam, nữ bệnh nhân ung thư chỉ còn sống được vài ngày. Trong những ngày cuối cùng của mình, cô không ngần ngại giúp đỡ những người sống sót đang tìm cách thoát khỏi bọn quái vật.","Kinh dị",R.raw.a_quiet_place,"28/06/2024", 50000));
        movies.add(new Movie(2,"Deadpool và Wolverine",R.drawable.deadpool3,"Wolverine đang hồi phục sau vết thương khi anh ấy đi ngang qua con đường với Deadpool mồm mép. Họ hợp sức để đánh bại kẻ thù chung.","Hành động",R.raw.deadpool3,"26/07/2024", 50000));
        movies.add(new Movie(3,"Kẻ Trộm Mặt Trăng 4",R.drawable.minion4,"Gru phải đối mặt với kẻ thù mới là Maxime Le Mal và Valentina đang lên kế hoạch trả thù anh, buộc gia đình anh phải lẩn trốn đi nơi khác. Bên cạnh việc đấu tranh bảo vệ gia đình, Gru đồng thời còn phải tìm ra điểm chung với thành viên mới nhất trong nhà đó là Gru Jr.","Hài hước",R.raw.minion4,"05/07/2024", 50000));
        movies.add(new Movie(4,"Những Mảnh Ghép Cảm Xúc 2",R.drawable.inside_out2,"Cuộc sống tuổi mới lớn của cô bé Riley lại tiếp tục trở nên hỗn loạn hơn bao giờ hết với sự xuất hiện của 4 cảm xúc hoàn toàn mới: Lo u, Ganh Tị, Xấu Hổ, Chán Nản. Mọi chuyện thậm chí còn rối rắm hơn khi nhóm cảm xúc mới này nổi loạn và nhốt nhóm cảm xúc cũ gồm Vui Vẻ, Buồn Bã, Giận Dữ, Sợ Hãi và Chán Ghét lại, khiến cô bé Riley rơi vào những tình huống dở khóc dở cười.","Hoạt hình",R.raw.inside_ou2,"14/06/2024", 75000));
        movies.add(new Movie(5,"Mùa Hè Đẹp Nhất",R.drawable.mua_he_dep_nhat,"Mùa hè năm ấy, chúng ta chẳng có gì trong tay ngoài tuổi trẻ, ước mơ, và…có nhau. Nhưng cũng chính mùa Hè ấy - mùa hè mang theo những điều mà ta chưa kịp hoàn thành… Bạn đã sẵn sàng gặp lại phiên bản của chính mình, trong những ngày Hè đẹp nhất của tuổi trẻ năm ấy và viết tiếp câu chuyện còn dở dang chưa?","Lãng mạn",R.raw.mua_he_dep_nhat,"28/06/2024", 75000));
        movies.add(new Movie(6,"Thám Tử Lừng Danh Conan: Ngôi Sao 5 Cánh 1 Triệu Đô",R.drawable.conan,"Trong khi đến Hakodate tham gia một giải kiếm đạo, Conan và Heiji đụng độ siêu trộm Kaito Kid - khi hắn đang nhắm tới một thanh kiếm Nhật được cất giấu trong nhà kho của một gia đình tài phiệt. Thi thể một tay buôn vũ khí khét tiếng được phát hiện với vết chém hình chữ thập, và trùng hợp thay, \"kho báu\" mà gã truy lùng dường như cũng có liên quan mật thiết đến thanh kiếm cổ mà Kid đang nhắm tới.","Trinh thám",R.raw.conan,"02/08/2024", 75000));
        movies.add(new Movie(7,"Ôi Ma Ơi: Hồi Kết",R.drawable.oi_ma_oi,"Rùng rợn nhưng không kém phần hài hước, bộ phim theo chân hành trình “bắt ma” đầy bí ẩn của hai chị em Panda, Pancake tại ngôi làng Sapayod đầy rẫy những sự việc kỳ quái.","Kinh dị",R.raw.oi_ma_oi,"12/07/2024", 75000));
        movies.add(new Movie(8,"Hijack 1971",R.drawable.hijack_1971,"Bộ phim khắc họa câu chuyện của những con người đấu tranh giành lấy sự sống trong hoàn cảnh cực kỳ khó khăn khi một chiếc máy bay chở khách bị cướp trên không phận Hàn Quốc vào năm 1971.","Hành động",R.raw.hijack_1971,"19/07/2024", 75000));
        return movies;
    }

    public static ArrayList<Movie> filterByCategory(Context context, String category) {
        ArrayList<Movie> filteredMovies = new ArrayList<>();
        ArrayList<Movie> allMovies = getAll(context);

        for (Movie movie : allMovies) {
            if (movie.getCategory().equalsIgnoreCase(category)) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }
}
