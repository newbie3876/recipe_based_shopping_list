import React, { useEffect, useState } from "react";
import axios from "axios";

const BookList = () => {
  // Būsena knygų sąrašui
  const [books, setBooks] = useState([]);

  // Fetch knygas iš backend'o naudojant Axios
  useEffect(() => {
    axios
      .get("/api/books")
      .then((response) => {
        setBooks(response.data); // Nustatome gautas knygas į būsena
      })
      .catch((error) => {
        console.error("There was an error fetching the books!", error);
      });
  }, []);

  return (
    <div>
      <h1>Books List</h1>
      <ul>
        {books.map((book) => (
          <li key={book.id}>{book.title}</li> // Atvaizduojame knygos pavadinimus
        ))}
      </ul>
    </div>
  );
};

export default BookList;
