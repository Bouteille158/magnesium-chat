import { Message } from "../types/Message";

export const getMessages = async () => {
  const response = await fetch(
    `${import.meta.env.VITE_SODIUM_API_URL}/messages`
  );
  const data: Message[] = await response.json();
  return data;
};

export const postMessage = async (message: Message) => {
  const response = await fetch(
    `${import.meta.env.VITE_SODIUM_API_URL}/messages`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(message),
    }
  );
  const data = await response.json();
  return data;
};
