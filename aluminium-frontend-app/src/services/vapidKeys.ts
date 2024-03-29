import axios from "axios";
const apiURL = `${import.meta.env.VITE_SODIUM_API_URL}/api`;

export async function getVapidPublicKey(): Promise<string | Error> {
  console.log(
    "Axios vapid token",
    axios.defaults.headers.common["Authorization"]
  );
  return await axios
    .get(`${apiURL}/vapidPublicKey`, {
      headers: {
        "content-type": "application/text",
      },
    })
    .catch((error) => {
      console.error("Error while fetching VAPID public key: ", error);
      return error;
    });
}
