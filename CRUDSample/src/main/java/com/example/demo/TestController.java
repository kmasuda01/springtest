package com.example.demo;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
//リクエストマッピング（RequestMapping）には、クライアントから受け付けたリクエスト URL 及び HTTP メソッドによって、
//Controller 内のどのメソッドに処理を渡すかを決める役割があります。
public class TestController {
	private JdbcTemplate jdbcTemplate;
	//@Autowired
    //private JdbcTemplate jdbcTemplate; とすると下記のコンストラクタがなくてもいい
	//コンストラタ
	@Autowired
	public TestController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//一覧画面表示
	//@RequestMapping(method=RequestMethod.GET)と同じ
	@GetMapping
	//Modelオブジェクトに追加される時の属性名はデフォルトで、クラス名の先頭を小文字にした値になります。
	//Modelオブジェクトとは、Springが用意するMapオブジェクトで、Viewに渡すオブジェクトを設定します。
	public String index(Model model) {
		String sql = "SELECT * FROM test_table";
		//List 要素数が決まっていない配列宣言方法　配列は要素数が決まっている
		//Map 辞書型　String：キー, Object：値
		//jdbcTemplateを用いてDBへアクセス
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		//modelmがlistをtestListという名前をつけてViewに値を渡してくれる
		model.addAttribute("testList", list);
		//htmlファイル名
		return "sample/index";
	}
	
	//新規入力フォームの表示
	@GetMapping("/form")
	//@ModelAttribute TestFormオブジェクトを格納する
	public String  form(@ModelAttribute TestForm testForm) {
		return "sample/form";
	}
	
	//新規入力データの保存
	@PostMapping("/form")
	public String create(TestForm testForm) {
		String sql = "INSERT INTO test_table(name, old) VALUES(?, ?);";
        jdbcTemplate.update(sql, testForm.getName(), testForm.getOld());
        return "redirect:/sample";
	}
	
	//編集フォームの表示
	@GetMapping("/edit/{id}")
	//URLマッピングで指定するURLに「{」と「}」で囲まれた部分がパラメータ名になり、
	//@PathVariableアノテーションのvalue属性にパラメータ名を指定することで、URLの部分文字列を取得することができます。
	public String edit(@ModelAttribute TestForm testForm, @PathVariable int id) {
		String sql = "SELECT * FROM test_table WHERE id = " + id;
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		testForm.setId((int)map.get("id"));
		testForm.setName((String)map.get("name"));
		testForm.setOld((int)map.get("old"));
		return "sample/edit";
	}
	
	//編集データの保存
	@PostMapping("/edit/{id}")
	public String update(TestForm testForm, @PathVariable int id) {
		String sql = "UPDATE test_table SET name = ?, old = ? WHERE id = " + id;
		jdbcTemplate.update(sql, testForm.getName(), testForm.getOld());
		return "redirect:/sample";
	}
	
	//データの削除
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		String sql = "DELETE from test_table WHERE id = " + id;
		jdbcTemplate.update(sql);
		return "redirect:/sample";
	}
}
