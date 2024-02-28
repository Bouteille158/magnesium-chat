export const formatTimestamp = (timestamp: string) => {
  const date = new Date(timestamp);
  // retourne la date au fomat HH:MM si le message a été envoyé aujourd'hui
  if (date.toDateString() === new Date().toDateString()) {
    return date.toLocaleTimeString();
  }
  // retourne la date au format JJ/MM/AAAA si le message a été envoyé avant aujourd'hui
  return date.toLocaleDateString();
};
