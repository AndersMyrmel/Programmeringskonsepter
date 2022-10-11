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
	String name;
	Integer id;
	Integer phone;
	String email;

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