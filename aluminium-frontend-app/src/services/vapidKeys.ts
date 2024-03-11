const apiURL = `${import.meta.env.VITE_SODIUM_API_URL}/api`;

export function getVapidPublicKey(): Promise<string> {
  return fetch(`${apiURL}/vapidPublicKey`, {
    method: "GET",
    headers: {
      "content-type": "application/text",
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.text();
    })
    .then((vapidPublicKey) => {
      console.log("vapidPublicKey returned in the request: ", vapidPublicKey);
      return vapidPublicKey;
    });
}
