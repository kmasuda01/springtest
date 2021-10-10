package com.example.demo;

//入力値を保持するためのフォームクラスを作成します。
//このフォームクラスを入れ物にして、ビュー、コントローラー、クライアントの間のデータのやり取りを行います。
public class TestForm {
	private Integer id;
	private String name;
	private Integer old;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getOld() {
        return old;
    }
    public void setOld(Integer old) {
        this.old = old;
    }
}
