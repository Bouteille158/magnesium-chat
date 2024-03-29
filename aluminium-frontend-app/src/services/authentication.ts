import axios from "axios";
import Cookies from "js-cookie";

const apiURL = `${import.meta.env.VITE_SODIUM_API_URL}`;

export async function authenticate(username: String, password: String) {
  try {
    const encodedCredentials = btoa(`${username}:${password}`);

    const response = await axios.post(
      apiURL + "/login",
      {},
      {
        headers: {
          Authorization: `Basic ${encodedCredentials}`,
        },
      }
    );

    console.log("Réponse de l'API", response.data);

    const token = response.data;

    // Stocker le token dans le localStorage
    Cookies.set("token", token, { secure: true });

    // Configurer axios pour inclure le token JWT dans l'en-tête Authorization de toutes les futures requêtes
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  } catch (error) {
    console.error("Erreur d'authentification", error);
  }
}
