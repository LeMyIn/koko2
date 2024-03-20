package com.ezen.day10_7;

import java.util.ArrayList;

public interface Service {


	ArrayList<Book_DTO> book_out();

	void binsert(String parameter, String parameter2, String parameter3, String parameter4, String parameter5,
			String parameter6);

	void board_insert(String name, String pw, String subject, String content);

	ArrayList<Board_DTO> boarder_list();

	Board_DTO board_view(String parameter);

	int board_update(String parameter, String parameter2);

	void board_update_do(String parameter, String string, String string2);

	void count(String parameter);


}
