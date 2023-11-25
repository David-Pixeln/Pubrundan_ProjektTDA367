import { CustomTheme } from '@constants/themes';
import { StyleSheet } from 'react-native';


export const style = (theme: CustomTheme) => StyleSheet.create({
  card: {
    width: "100%",
    padding: 20,
    
    display: 'flex',
    gap: 16,

    borderRadius: 20,
    overflow: 'hidden',

    backgroundColor: theme.colors.background.s600,
  },
  cardHeader: {
    width: '100%',
    display: "flex",
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  headerItemContainer: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    gap: 5,
  },
  authorText: {
    fontWeight: 'bold',
  },
  profilePictureContainer: {
    width: 40,
    aspectRatio: 1,
    
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',

    borderRadius: 50,
    overflow: 'hidden',
  },
  profilePicture: {
    width: '100%',
    height: '100%',
  },
  postImageContainer: {
    aspectRatio: 1,

    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    
    borderRadius: 20,
    overflow: 'hidden',

    backgroundColor: 'red',
  },
  postImage: {
    height: '100%',
    width: '100%',
  },
})