package firsProject.booksProject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
public class Publisher {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
   private String name;
   @OneToMany(mappedBy = "publisher",cascade = CascadeType.ALL)
   private Set<Book> books=new HashSet<>();

   public Publisher() {
   }

   public Publisher(long id, String name) {
      this.id = id;
      this.name = name;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Set<Book> getBooks() {
      return books;
   }

   public void setBooks(Set<Book> books) {
      this.books = books;
   }

   @Override
   public String toString() {
      return "Publisher{" +
              "id=" + id +
              ", name='" + name + '\'' +
              '}';
   }
}
