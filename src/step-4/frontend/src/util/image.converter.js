const dataUrlToByteArray = (dataUrl) => {
  const [, base64Data] = dataUrl.split(',');
  const binaryData = atob(base64Data);
  const byteArray = new Uint8Array(binaryData.length);

  for (let i = 0; i < binaryData.length; i++) {
    byteArray[i] = binaryData.charCodeAt(i);
  }
  console.log(byteArray[0]);
  console.log(byteArray);
  return byteArray;
};

const byteArrayToImage = (byteArray) => {
  const blob = new Blob([byteArray], { type: 'image/jpeg' });
  return URL.createObjectURL(blob);
};

export { dataUrlToByteArray, byteArrayToImage };
