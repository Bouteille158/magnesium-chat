import { Message } from "../types/Message";
import Cookies from "js-cookie";

export const getMessages = async () => {
  // TODO use axios instead of fetch
  const response = await fetch(
    `${import.meta.env.VITE_SODIUM_API_URL}/messages`,
    {
      headers: {
        Authorization: `Bearer ${Cookies.get("token")}`,
      },
    }
  );
  const data: Message[] = await response.json();

  return data.sort((a, b) => {
    return new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime();
  });
};

export const postMessage = async (message: Message) => {
  // TODO use axios instead of fetch
  const response = await fetch(
    `${import.meta.env.VITE_SODIUM_API_URL}/messages`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${Cookies.get("token")}`,
      },
      body: JSON.stringify(message),
    }
  );
  const data = await response.json();
  return data;
};
