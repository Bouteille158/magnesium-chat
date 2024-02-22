import React from "react";

interface SpacerProps {
  height?: string;
  width?: string;
}

const Spacer: React.FC<SpacerProps> = ({ height = "1em", width = "1em" }) => {
  return <div style={{ height, width }} />;
};

export default Spacer;
