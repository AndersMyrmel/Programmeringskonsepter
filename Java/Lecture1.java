import java.util.List;

class Lecture1 {
	public static void main(String[] args) {
		Person p = new Person();
		p.setName("John Doe");
		p.setId(1);
		p.setPhones(12345678);
		p.setEmail("JohnDoe@Example.com");
		System.out.println(p.name);
	}
}

class Person {
	public String name;
	public Integer id;
	public Integer phone;
	public String email;

	public void setName(String s) {
		this.name = s;
	}

	public void setId(Integer x) {
		this.id = x;
	}

	public void setPhones(Integer x) {
		this.phone = x;
	}

	public void setEmail(String s) {
		this.email = s;
	}
}