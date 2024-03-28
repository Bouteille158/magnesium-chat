import axios from "axios";
import Cookies from "js-cookie";

export async function authenticate(username: String, password: String) {
  try {
    const response = await axios.post("/login", {
      username,
      password,
    });

    const token = response.data;

    // Stocker le token dans le localStorage
    Cookies.set("token", token, { secure: true });

    // Configurer axios pour inclure le token JWT dans l'en-tête Authorization de toutes les futures requêtes
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  } catch (error) {
    console.error("Erreur d'authentification", error);
  }
}
