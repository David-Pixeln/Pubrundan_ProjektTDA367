import { Text as RNText, TextProps} from 'react-native';
import { useContext } from 'react';
import { ThemeContext } from '@constants/themes';


export function Text({ children, style, ...props }: TextProps) {
  const theme = useContext(ThemeContext);
  
  if (!style || !("color" in style)) {
    style = { ...(style as object), color: theme.colors.text };
  }

  return (
    <RNText style={style} {...props}>
      {children}
    </RNText>
  );
}
